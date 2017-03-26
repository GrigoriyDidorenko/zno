package ua.com.zno.online.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.zno.online.DTOs.EntityToDTO;
import ua.com.zno.online.DTOs.SubjectDTO;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.services.UserService;

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
    public TestDTO getTest(@PathVariable Long testId) throws ServerException {
        return guestService.getTest(testId);
    }

    @GetMapping("subject")
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
