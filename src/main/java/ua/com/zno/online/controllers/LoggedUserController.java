package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.controllers.filter.RequestFilter;
import ua.com.zno.online.exceptions.UserException;
import ua.com.zno.online.services.user.DefaultLoggedUserService;
import ua.com.zno.online.services.user.LoggedUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by quento on 28.03.17.
 */
@RestController(value = "user")
public class LoggedUserController {

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
}
