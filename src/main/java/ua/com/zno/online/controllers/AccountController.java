package ua.com.zno.online.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by quento on 29.03.17.
 */

@Controller
public class AccountController {

    @PostMapping(value = "register")
    public void register() {
        //TODO
    }

    @PostMapping(value = "login")
    public void login() {
        //TODO
    }
}
