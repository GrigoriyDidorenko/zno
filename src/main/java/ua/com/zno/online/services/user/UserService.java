package ua.com.zno.online.services.user;

import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */
public interface UserService {

    @Transactional(readOnly = true)
    TestDTO getTest(Long id) throws ZnoServerException;

    @Transactional(readOnly = true)
    List<Subject> getSubjects();

    @Transactional(readOnly = true)
    List<Test> getTestsBySubject(Long subjectId);

    @Transactional(readOnly = true)
    TestDTO getShuffledTestBySubject(Long subjectId) throws ZnoUserException;
}
