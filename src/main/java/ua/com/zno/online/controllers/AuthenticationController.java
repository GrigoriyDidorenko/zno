package ua.com.zno.online.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Controller
public class AuthenticationController {

    @GetMapping("vklogin")
    public String login(@RequestParam(value = "userId") Long userId, @RequestParam(value = "email") String email, @RequestParam(value = "hash") String hash){
        System.out.println();
        return "";
    }


}
