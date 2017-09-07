package ua.com.zno.online.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.SubjectDTO;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.DTOs.notification.SubjectFailedQuestionAmountDTO;
import ua.com.zno.online.controllers.filter.RequestFilter;
import ua.com.zno.online.domain.Subject;
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

    private final RequestFilter requestFilter;
    private final LoggedUserService defaultLoggedUserService;

    @Autowired
    public LoggedUserController(RequestFilter requestFilter, LoggedUserService defaultLoggedUserService) {
        this.requestFilter = requestFilter;
        this.defaultLoggedUserService = defaultLoggedUserService;
    }

    @PostMapping(value = "result")
    public ResponseEntity<Double> acceptTestResult(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws ZnoUserException {
//        if (requestFilter.isSpamming(request.getLocalAddr())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        double mark = defaultLoggedUserService.processTestResult(testResultDTO);
        return new ResponseEntity<>(mark, HttpStatus.OK);
    }

    @PostMapping(value = "failed/questions", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> acceptFailedQuestions(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws ZnoUserException {
//        if (requestFilter.isSpamming(request.getLocalAddr())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        defaultLoggedUserService.saveFailedQuestionsResult(testResultDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("statistics")
    public List<SubjectStatistics> getStatistics() throws ZnoUserException {
        return defaultLoggedUserService.getStatistics();
    }

    @GetMapping("failed/notification")
    public List<SubjectFailedQuestionAmountDTO> getNotificationFailed() throws ZnoUserException, JsonProcessingException {

        return defaultLoggedUserService.getNotificationFailed();
    }

    @GetMapping("failed/questions/{subjectId}")
    public TestDTO getFailedQuestionsTest(@PathVariable Long subjectId) {
        return defaultLoggedUserService.getFailedQuestionsBySubject(subjectId);
    }

    //TODO not needed
    @GetMapping("failed/questions")
    public TestDTO getFailedQuestionsTest() {
        return defaultLoggedUserService.getFailedQuestions();
    }
}
