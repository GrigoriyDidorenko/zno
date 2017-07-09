package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.UserDTO;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.exceptions.UserException;
import ua.com.zno.online.services.security.SecurityService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("vkLogin")
    public ResponseEntity<Void> vkLogin(@RequestParam String code, Principal principal) throws ServerException, IOException, UserException {
        if (principal != null) return null;
        securityService.authenticateVkUser(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("googleLoginn")
    public ResponseEntity<Void> googleLoginn(@RequestParam(name = "id_token") String idToken, Principal principal) throws GeneralSecurityException, IOException, UserException {
        if (principal != null) return null;
        securityService.authenticateGoogleUser(idToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO delete this
    @GetMapping("gLoginPage")
    public String gLoginPage() {
        return "googleLogin.html";
    }

    @PostMapping("facebookLogin") //TODO button works only from second attempt
    public ResponseEntity<Void> facebookLogin(@RequestParam String accessToken, @RequestParam String userId,
                                              @RequestParam String name,
                                              @RequestParam String email, Principal principal) throws ServerException, IOException, UserException {
        if (principal != null) return null;
        securityService.authenticateFacebookUser(accessToken, userId, name, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("fLoginPage")
    public String fLoginPage() {
        return "fbLogin.html";
    }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> registration(@RequestBody @Valid UserDTO userDTO) throws ServerException, NoSuchAlgorithmException {
        securityService.register(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("confirmRegistration/{email}/{hash}")
    public ResponseEntity<Void> confirmRegistration(@PathVariable String email, @PathVariable String hash) throws ServerException, NoSuchAlgorithmException {
        securityService.confirmRegistration(email, hash);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestParam String email) throws ServerException {
        securityService.resetPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("changePassword")
    public ResponseEntity<Void> changePassword(@RequestParam String email, @RequestParam String oldPsswrd, @RequestParam String newPsswrd) throws ServerException {
        securityService.changePassword(email, oldPsswrd, newPsswrd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
