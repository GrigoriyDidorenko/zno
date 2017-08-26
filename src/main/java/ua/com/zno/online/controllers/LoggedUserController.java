package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.controllers.filter.RequestFilter;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.DTOs.statistic.SubjectStatistics;
import ua.com.zno.online.services.user.LoggedUserService;
import ua.com.zno.online.util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by quento on 28.03.17.
 */
@RestController
@RequestMapping("user")
public class LoggedUserController {

    @Autowired
    private RequestFilter requestFilter;

    @Autowired
    private LoggedUserService defaultLoggedUserService;

    @PostMapping("result")
    public ResponseEntity<Double> acceptTestResult(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws ZnoUserException {
        if (requestFilter.isSpamming(request.getLocalAddr())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        double mark = defaultLoggedUserService.processTestResult(testResultDTO);
        return new ResponseEntity<>(mark, HttpStatus.OK);
    }

    @PostMapping("failed/questions")
    public ResponseEntity<Void> acceptFailedQuestions(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws ZnoUserException {
        if (requestFilter.isSpamming(request.getLocalAddr())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        defaultLoggedUserService.saveFailedQuestionsResult(testResultDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("statistics")
    public List<SubjectStatistics> getStatistics(Principal principal) throws ZnoUserException {
        if (principal == null) throw new ZnoUserException("you are not authenticated!");

        return defaultLoggedUserService.getStatistics();
    }

    @GetMapping("failed/notification")
    public Map<String, Integer> getNotificationFailed(Principal principal) throws ZnoUserException {
        if (principal == null) throw new ZnoUserException("you are not authenticated!");

        return defaultLoggedUserService.getNotificationFailed();
    }

    @GetMapping("failed/questions/{subjectId}")
    public TestDTO getFailedQuestionsTest(@PathVariable Long subjectId) {
        return defaultLoggedUserService.getFailedQuestionsBySubject(subjectId);
    }

    @GetMapping("failed/questions")
    public TestDTO getFailedQuestionsTest() {
        return defaultLoggedUserService.getFailedQuestions();
    }
}
