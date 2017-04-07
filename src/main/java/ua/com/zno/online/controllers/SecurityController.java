package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.zno.online.domain.User;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.services.authentication.SecurityService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;


    @GetMapping("vkLogin")
    public String vkLogin(@RequestParam String code, Principal principal) throws ServerException, IOException {
        if (principal != null) return "some_page_that_says_'you are already logged_in'";
        securityService.authenticateVkUser(code);

        return "user_profile_or_smth";
    }

    @GetMapping("registration") //TODO should be POST
    public String registration(@RequestParam String name, @RequestParam(required = false) String surname,
                               @RequestParam String email, @RequestParam String password) throws ServerException, NoSuchAlgorithmException {
        securityService.register(name, surname, email, password);

        return "login";
    }

    @GetMapping("confirmRegistration/{email}/{hash}")
    public String confirmRegistration(@PathVariable String email, @PathVariable String hash) throws ServerException, NoSuchAlgorithmException {
        securityService.confirmRegistration(email, hash);

        return "user_profile_or_smth";
    }




}
