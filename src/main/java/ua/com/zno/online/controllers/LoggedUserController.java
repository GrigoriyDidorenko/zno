package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.controllers.filter.RequestFilter;
import ua.com.zno.online.exceptions.UserException;
import ua.com.zno.online.DTOs.statistic.Statistics;
import ua.com.zno.online.services.user.LoggedUserService;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by quento on 28.03.17.
 */
@RestController(value = "user")  //FIXME does not work with "*/user/*". Works without "user" in path
public class LoggedUserController { //FIXME UserException{message='you are not authenticated!' must redirect to login

    @Autowired
    private RequestFilter requestFilter;

    @Autowired
    private LoggedUserService defaultLoggedUserService;

    @PostMapping("result")
    public ResponseEntity<Void> acceptTestResult(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws UserException {
        if (requestFilter.isSpamming(request.getLocalAddr())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        defaultLoggedUserService.saveTestResult(testResultDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("failed/questions")
    public ResponseEntity<Void> acceptFailedQuestions(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws UserException {
        if (requestFilter.isSpamming(request.getLocalAddr())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        defaultLoggedUserService.saveFailedQuestionsResult(testResultDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("statistics")
    @ResponseBody
    public Statistics getStatistics(Principal principal) throws UserException {
        if (principal == null) throw new UserException("you are not authenticated!");

        return defaultLoggedUserService.getStatistics();
    }
}
