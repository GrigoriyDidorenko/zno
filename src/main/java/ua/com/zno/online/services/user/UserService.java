package ua.com.zno.online.services.user;

import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.exceptions.ServerException;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */
public interface UserService {

    TestDTO getTest(Long id) throws ServerException;

    List<Subject> getSubjects();

    List<Test> getTestsBySubject(Long subjectId);
}
