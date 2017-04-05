package ua.com.zno.online.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.zno.online.DTOs.EntityToDTO;
import ua.com.zno.online.DTOs.UserDTO;
import ua.com.zno.online.domain.User;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by obalitskiy on 4/5/17.
 */

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private EntityToDTO entityToDTO;

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
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(userDTO.getLogin()));

        if (!user.isPresent()){
            User userToPersist = entityToDTO.DTOToEntity(userDTO, User.class);
            userRepository.save(userToPersist);
            user = Optional.of(userToPersist);
        }

        try {

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getSecret(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            //return "login";
        }
    }

    private String makeVkUrl(String code){

        return "https://oauth.vk.com/access_token?" +
                "client_id=" +
                env.getProperty("client.id") +
                "&" +
                "client_secret=" +
                env.getProperty("client.secret") +
                "&" +
                "redirect_uri=" +
                env.getProperty("redirect.uri") +
                "&" +
                "code=" +
                code;
    }
}
