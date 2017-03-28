package ua.com.zno.online.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by quento on 28.03.17.
 */
@RestController(value = "user")
public class LoggedUserController {

    @PostMapping("result")
    public void acceptTestResult() {
        //TODO
    }
}
