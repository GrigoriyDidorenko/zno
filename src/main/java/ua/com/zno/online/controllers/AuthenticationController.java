package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.services.authentication.AuthenticationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("vkLogin")
    public String login(@RequestParam String code, Principal principal) throws ServerException, IOException {
        if (principal != null) return "some_page_that_says_'you are already logged_in'";
        authenticationService.authenticateVkUser(code);

        return "user_profile_or_smth";
    }

}
