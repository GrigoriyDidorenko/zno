package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.services.security.SecurityService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

//TODO DEAL with returning the response
    @GetMapping("vkLogin")
    public void vkLogin(@RequestParam String code, Principal principal) throws ServerException, IOException {
        if (principal != null) return;
        securityService.authenticateVkUser(code);
    }

    @GetMapping("registration") //TODO should be POST
    public void registration(@RequestParam String name, @RequestParam(required = false) String surname,
                               @RequestParam String email, @RequestParam String password) throws ServerException, NoSuchAlgorithmException {
        securityService.register(name, surname, email, password);
    }

    @GetMapping("confirmRegistration/{email}/{hash}")
    public void confirmRegistration(@PathVariable String email, @PathVariable String hash) throws ServerException, NoSuchAlgorithmException {
        securityService.confirmRegistration(email, hash);
    }

    @GetMapping("resetPassword")
    public void resetPassword(@RequestParam String email) throws ServerException {
        securityService.resetPassword(email);
    }

    @GetMapping("changePassword") //TODO should be POST
    public void changePassword(@RequestParam String email, @RequestParam String oldPsswrd, @RequestParam String newPsswrd) throws ServerException {
        securityService.changePassword(email, oldPsswrd, newPsswrd);
    }

}
