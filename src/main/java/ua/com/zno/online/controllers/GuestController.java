package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.DTOs.SubjectDTO;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.services.user.UserService;
import ua.com.zno.online.util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quento on 26.03.17.
 */

@Controller
@RequestMapping("api")
public class GuestController {

    @Autowired
    private UserService guestService;

    @Autowired
    private EntityToDTO entityToDTO;


    @GetMapping("test/{testId}")
    @ResponseBody
    public TestDTO getTest(@PathVariable Long testId) throws ZnoServerException {
        return guestService.getTest(testId);
    }

    @GetMapping("subject")
    @ResponseBody
    public List<SubjectDTO> getSubjects() {
        return guestService.getSubjects().stream()
                .map(subject -> entityToDTO.convertToDTO(subject, SubjectDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("subject/{subjectId}")
    @ResponseBody
    public List<TestDTO> getTestsBySubject(@PathVariable Long subjectId) {
        return guestService.getTestsBySubject(subjectId).stream()
                .map(test -> entityToDTO.convertToDTO(test, TestDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("brainstorm/{subjectId}")
    @ResponseBody
    public TestDTO getShuffledTest(@PathVariable Long subjectId) throws ZnoUserException {
        return guestService.getShuffledTestBySubject(subjectId);
    }

    @PostMapping("result")
    public ResponseEntity<Double> acceptTestResult(HttpServletRequest request, @RequestBody TestResultDTO testResultDTO) throws ZnoUserException {
        double mark = guestService.processTestResult(testResultDTO);
        return new ResponseEntity<>(mark, HttpStatus.OK);
    }
}
