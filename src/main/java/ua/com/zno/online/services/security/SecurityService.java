package ua.com.zno.online.services.security;

//import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ua.com.zno.online.DTOs.FBResponseDTO;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.DTOs.UserDTO;
import ua.com.zno.online.controllers.SecurityController;
import ua.com.zno.online.domain.mail.Mail;
import ua.com.zno.online.domain.user.Authority;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.zno.online.services.mail.MailService;
import ua.com.zno.online.util.SecurityUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by obalitskiy on 4/5/17.
 */

@Service
public class SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private EntityToDTO entityToDTO;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void authenticateVkUser(String code) throws ZnoServerException, IOException, ZnoUserException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(makeVkUrl(code), String.class);
        } catch (Exception e) {
            throw new ZnoServerException("Can not authenticate via VK");
        }

        if (HttpStatus.OK != response.getStatusCode()) throw new ZnoServerException("Can not authenticate via VK");

        UserDTO userDTO = new ObjectMapper().readValue(response.getBody(), UserDTO.class);
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(userDTO.getLogin()));

        if (!user.isPresent()) {
            User userToPersist = entityToDTO.DTOToEntity(userDTO, User.class);
            userToPersist.setEnabled(true);
            userRepository.save(userToPersist);
            user = Optional.of(userToPersist);
        }

        authorizateUser(user.get());
    }

    @Transactional
    public void authenticateGoogleUser(String idToken) throws GeneralSecurityException, IOException, ZnoUserException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(env.getProperty("google.client.id")))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        try {
            GoogleIdToken idTokenVerified = verifier.verify(idToken);
            if (idTokenVerified != null) {
                GoogleIdToken.Payload payload = idTokenVerified.getPayload();

//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                boolean emailVerified = payload.getEmailVerified();

                String login = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("given_name");
                String surname = (String) payload.get("family_name");

                Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(login));

                if (!user.isPresent()) {
                    User userToPersist = new User(name, login, email, login, LocalDateTime.now(), true, Collections.singleton(Authority.USER));
                    userRepository.save(userToPersist);
                    user = Optional.of(userToPersist);
                }

                UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), user.get().isEnabled(), true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));

                Authentication auth =
                        new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                LOG.error("somehow idTokenVerified is null");
            }
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new ZnoUserException("Could not authentiate google user");
        }
    }

    @Transactional
    public void authenticateFacebookUser(String accessToken, String userId, String name, String email) throws ZnoServerException, IOException, ZnoUserException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(makeFbUrl(accessToken), String.class);
        } catch (Exception e) {
            throw new ZnoServerException("Can not authenticate via FB");
        }

        if (HttpStatus.OK != response.getStatusCode()) throw new ZnoServerException("Can not authenticate via FB");

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        FBResponseDTO fbResponseDTO = om.readValue(response.getBody(), FBResponseDTO.class);
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(fbResponseDTO.getUserId()));

        if (!user.isPresent()) {
            User userToPersist = new User(name, userId, email, accessToken, LocalDateTime.now(),
                    true, Collections.singleton(Authority.USER));
            userRepository.save(userToPersist);
            user = Optional.of(userToPersist);
        }

        authorizateUser(user.get());
    }

    private void authorizateUser(User user) throws ZnoUserException {
        try {

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), user.isEnabled(), true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new ZnoUserException("Could not authentiate vk user");
        }
    }

    @Transactional
    public void register(UserDTO userDTO) throws
            ZnoUserException, NoSuchAlgorithmException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(userDTO.getEmail()));
        if (user.isPresent())
            throw new ZnoUserException("This email already registered!");

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User userToPersist = new User(userDTO.getEmail().substring(0, userDTO.getEmail().indexOf("@")),
                userDTO.getEmail(), userDTO.getEmail(), hashedPassword, LocalDateTime.now(), false, Collections.singleton(Authority.USER));
        userRepository.save(userToPersist);
        mailService.sendEmail(new Mail(userDTO.getEmail(), "Підтвердіть реєстрацію для 'zno.net.ua'.", this.createConfirmationContent(userDTO.getEmail())));
    }

    @Transactional
    public void confirmRegistration(String email, String hash) throws NoSuchAlgorithmException, ZnoServerException, ZnoUserException {
        if (!hash.equals(SecurityUtils.createSHA256URLSafeHash(email + env.getProperty("vk.client.secret")))) {
            throw new ZnoServerException("not valid hash");
        }

        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (!user.isPresent()) throw new ZnoServerException("user does not exists");
        if (user.get().isEnabled()) throw new ZnoServerException("user account already activated");

        user.get().setEnabled(true);
        userRepository.save(user.get());
        mailService.sendEmail(new Mail(email, "Ви успішно зареєстровані на 'zno.net.ua'.", createSuccessRegistrationContent()));
        authorizateUser(user.get());
    }

    @Transactional
    public ResponseEntity<Void> resetPassword(String email) throws ZnoServerException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        //TODO return different statuses
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!user.get().isEnabled()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = RandomStringUtils.random(5, characters);
        String hashedPassword = passwordEncoder.encode(password);
        user.get().setPassword(hashedPassword);
        userRepository.save(user.get());
        mailService.sendEmail(new Mail(email, "Відновлення паролю для 'zno.net.ua'", createResetContent(password)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public void changePassword(String email, String oldPsswrd, String newPsswrd) throws ZnoServerException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (!user.isPresent()) throw new ZnoServerException("User with this email does not exist");
        if (!user.get().isEnabled()) throw new ZnoServerException("Account is not enabled yet");

        if (!passwordEncoder.matches(oldPsswrd, user.get().getPassword())) {
            throw new ZnoServerException("Password is not correct");
        }

        String hashedPassword = passwordEncoder.encode(newPsswrd);
        user.get().setPassword(hashedPassword);
        userRepository.save(user.get());
        mailService.sendEmail(new Mail(email, "Зміна паролю для 'zno.net.ua'", createChangePasswordContent()));
    }

    private String createChangePasswordContent() {
        //String beautyHtml = ...
        return "Ви успішно змінили пароль, якщо ви цього не робили, будь ласка, повідомте за номером 0978715858";
    }

    private String createSuccessRegistrationContent() {
        //String beautyHtml = ...
        return "Ви успішно зареєстровані на 'zno.net.ua'.";
    }

    private String createConfirmationContent(String email) throws NoSuchAlgorithmException {
        //String beautyHtml = ...
        return env.getProperty("host.uri") + "confirmRegistration/" + email + "/" + SecurityUtils.createSHA256URLSafeHash(email + env.getProperty("vk.client.secret"));
    }

    private String createResetContent(String password) {
        //String beautyHtml = ...
        return "Ваш тимчасовий пароль: " + password + ". Ви можете змінити його в особистому кабінеті:\n" +
                env.getProperty("host.uri") + "changePasswordPage";
    }

    private String makeVkUrl(String code) {

        return "https://oauth.vk.com/access_token?" +
                "client_id=" +
                env.getProperty("vk.client.id") +
                "&" +
                "client_secret=" +
                env.getProperty("vk.client.secret") +
                "&" +
                "redirect_uri=" +
                env.getProperty("host.uri") + "vkLogin" +
                "&" +
                "code=" +
                code;
    }

    private String makeFbUrl(String token) {
        return "https://graph.facebook.com/debug_token?" +
                "input_token=" +
                token +
                "&" +
                "access_token=" +
                "314517038979180|74755874ac5ee52caedbe35ea9c9df1f";
    }
}
