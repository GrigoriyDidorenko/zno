package ua.com.zno.online.services.security;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.DTOs.UserDTO;
import ua.com.zno.online.domain.user.Authority;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.zno.online.services.mail.MailService;
import ua.com.zno.online.util.SecurityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by obalitskiy on 4/5/17.
 */

@Service
public class SecurityService {

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
    public void authenticateVkUser(String code) throws ServerException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(makeVkUrl(code), String.class);
        } catch (Exception e){
            throw new ServerException("Can not authenticate via VK");
        }

        if (HttpStatus.OK !=response.getStatusCode()) throw new ServerException("Can not authenticate via VK");

        UserDTO userDTO = new ObjectMapper().readValue(response.getBody(), UserDTO.class);
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(userDTO.getVkId()));

        if (!user.isPresent()){
            User userToPersist = entityToDTO.DTOToEntity(userDTO, User.class);
            userToPersist.setEnabled(true);
            userRepository.save(userToPersist);
            user = Optional.of(userToPersist);
        }

        try {

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), user.get().isEnabled(), true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            //return "login";
        }
    }

    @Transactional
    public void register(String name, String surname, String email,  String password) throws ServerException, NoSuchAlgorithmException {
        //TODO validate email
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (user.isPresent()) throw new ServerException("This email already registered!"); //TODO not sure how to make feedback

        String hashedPassword = passwordEncoder.encode(password);
        User userToPersist = new User(name, surname, email, email, hashedPassword, LocalDateTime.now(), false, Collections.singleton(Authority.USER));
        userRepository.save(userToPersist);
        mailService.sendEmail(email, "Підтвердіть реєстрацію для 'zno.net.ua'.", createConfirmationContent(email));
    }

    @Transactional
    public void confirmRegistration(String email, String hash) throws NoSuchAlgorithmException, ServerException {
        if (!hash.equals(SecurityUtils.createSHA256URLSafeHash(email + env.getProperty("vk.client.secret")))){
            throw new ServerException("not valid hash");
        }

        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (!user.isPresent()) throw new ServerException("user does not exists");
        if (user.get().isEnabled()) throw new ServerException("user account already activated");

        user.get().setEnabled(true);
        userRepository.save(user.get());
        mailService.sendEmail(email, "Ви успішно зареєстровані на 'zno.net.ua'.", createSuccessRegistrationContent());
    }

    @Transactional
    public void resetPassword(String email) throws ServerException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (!user.isPresent()) throw new ServerException("User with this email does not exist");
        if (!user.get().isEnabled()) throw new ServerException("Account is not enabled yet");

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = RandomStringUtils.random( 5, characters );
        String hashedPassword = passwordEncoder.encode(password);
        user.get().setPassword(hashedPassword);
        userRepository.save(user.get());
        mailService.sendEmail(email, "Відновлення паролю для 'zno.net.ua'", createResetContent(password));
    }

    @Transactional
    public void changePassword(String email, String oldPsswrd, String newPsswrd) throws ServerException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (!user.isPresent()) throw new ServerException("User with this email does not exist");
        if (!user.get().isEnabled()) throw new ServerException("Account is not enabled yet");

        if (!passwordEncoder.matches(oldPsswrd, user.get().getPassword())){
            throw new ServerException("Password is not correct");
        }

        String hashedPassword = passwordEncoder.encode(newPsswrd);
        user.get().setPassword(hashedPassword);
        userRepository.save(user.get());
        mailService.sendEmail(email, "Зміна паролю для 'zno.net.ua'", createChangePasswordContent());
    }

    private String createChangePasswordContent(){
        //String beautyHtml = ...
        return "Ви успішно змінили пароль, якщо ви цього не робили, будь ласка, повідомте за номером 0978715858";
    }

    private String createSuccessRegistrationContent(){
        //String beautyHtml = ...
        return "Ви успішно зареєстровані на 'zno.net.ua'. Автризуйтеся і скоріше приступайте до роботи";
    }

    private String createConfirmationContent(String email) throws NoSuchAlgorithmException {
        //String beautyHtml = ...
        return env.getProperty("host.uri") + "confirmRegistration/" + email + "/" + SecurityUtils.createSHA256URLSafeHash(email + env.getProperty("vk.client.secret"));
    }

    private String createResetContent(String password){
        //String beautyHtml = ...
        return "Ваш тимчасовий пароль: " + password + ". Ви можете змінити його в особистому кабінеті:\n" +
                env.getProperty("host.uri") + "changePasswordPage";
    }

    private String makeVkUrl(String code){

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
}
