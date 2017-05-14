package ua.com.zno.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.DTOs.SubjectDTO;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.services.user.GuestService;
import ua.com.zno.online.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quento on 26.03.17.
 */

@Controller
public class GuestController {

    @Autowired
    private UserService guestService;

    @Autowired
    private EntityToDTO entityToDTO;


    @GetMapping("test/{testId}")
    @ResponseBody
    public TestDTO getTest(@PathVariable Long testId) throws ServerException {
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
    public List<TestDTO> getTestsBySubject(@PathVariable Long subjectId) {
        return guestService.getTestsBySubject(subjectId).stream()
                .map(test -> entityToDTO.convertToDTO(test, TestDTO.class))
                .collect(Collectors.toList());
    }
}
