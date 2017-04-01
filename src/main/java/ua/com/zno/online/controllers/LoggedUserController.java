package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.controllers.filter.RequestFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by quento on 28.03.17.
 */
@RestController(value = "user")
public class LoggedUserController {

    @Autowired
    private RequestFilter requestFilter;

    @PostMapping("result")
    public ResponseEntity<String> acceptTestResult(HttpServletRequest request, @RequestBody TestDTO testDTO) {
        if (requestFilter.isSpamming(request.getLocalAddr())) {
            return new ResponseEntity<>("You already spam this endpoint", HttpStatus.BAD_REQUEST);
        }

        //TODO

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
