package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class    AuthenticationController {
    /*@GetMapping("vkLogin")
    public String login(@RequestParam(value = "access_token") Long at,
                        @RequestParam(value = "expires_in") Long expires_in, @RequestParam(value = "user_id") Long user_id,
                        @RequestParam(value = "email") Long email){

        System.out.println();
        return "";
    }*/

    @Autowired
    UserDetailsService userDetailsService;


    @GetMapping("vkLogin")
    public String login(@RequestParam String code) throws Exception {
        System.out.println(2);

        sendGet("https://oauth.vk.com/access_token?client_id=5948360&redirect_uri=http://localhost:8082/vkLogin&client_secret=InVGCG2rDUR7pwCWPyPo&code=" + code);
        System.out.println();

        try {
            UserDetails user = userDetailsService.loadUserByUsername("root");

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e){
            SecurityContextHolder.getContext().setAuthentication(null);
            return "login";
        }


        return "";
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }



}
