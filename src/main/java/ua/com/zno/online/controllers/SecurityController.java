package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.UserDTO;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.repository.UserRepository;
import ua.com.zno.online.services.security.SecurityService;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("vkLogin")
    public ResponseEntity<Void> vkLogin(@RequestParam String code, Principal principal) throws ZnoServerException, IOException, ZnoUserException {
        if (principal != null) return null;
        securityService.authenticateVkUser(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("googleLogin")
    public ResponseEntity<Void> googleLogin(@RequestParam(name = "id_token") String idToken, Principal principal) throws GeneralSecurityException, IOException, ZnoUserException {
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
                                              @RequestParam String email, Principal principal) throws ZnoServerException, IOException, ZnoUserException {
        if (principal != null) return null;
        securityService.authenticateFacebookUser(accessToken, userId, name, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("fLoginPage")
    public String fLoginPage() {
        return "fbLogin.html";
    }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> registration(@RequestBody @Valid UserDTO userDTO) throws ZnoUserException, NoSuchAlgorithmException {
        securityService.register(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("confirmRegistration/{email}/{hash}")
    public void confirmRegistration(HttpServletResponse response, @PathVariable String email, @PathVariable String hash) throws ZnoServerException, NoSuchAlgorithmException, ZnoUserException, IOException {
        securityService.confirmRegistration(email, hash);
        response.sendRedirect("/statistics.html");
    }

    @GetMapping("loginStatus")
    @ResponseBody
    public LoginStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            return new LoginStatus(true, userRepository.findUserByEmail(auth.getName()).getName());
        } else {
            return new LoginStatus(false, null);
        }
    }

    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<LoginStatus> login(@RequestBody UserDTO userDTO) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        User details = userRepository.findUserByEmail(userDTO.getEmail());
        token.setDetails(details);

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new ResponseEntity<>(new LoginStatus(auth.isAuthenticated(), auth.getName()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new LoginStatus(false, null, "Bad credentials"), HttpStatus.BAD_REQUEST);
        } catch (DisabledException e) {
            return new ResponseEntity<>(new LoginStatus(false, null, "User is not activated"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestParam String email) throws ZnoServerException {
        return securityService.resetPassword(email);
    }

    @PostMapping("changePassword")
    public ResponseEntity<Void> changePassword(@RequestParam String email, @RequestParam String oldPsswrd, @RequestParam String newPsswrd) throws ZnoServerException {
        securityService.changePassword(email, oldPsswrd, newPsswrd);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public class LoginStatus {

        private final boolean loggedIn;
        private final String username;
        private String reason;

        public LoginStatus(boolean loggedIn, String username) {
            this.loggedIn = loggedIn;
            this.username = username;
        }

        public LoginStatus(boolean loggedIn, String username, String reason) {
            this.loggedIn = loggedIn;
            this.username = username;
            this.reason = reason;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public String getUsername() {
            return username;
        }

        public String getReason() {
            return reason;
        }
    }

}
