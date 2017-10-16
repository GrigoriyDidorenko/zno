package ua.com.zno.online.services.user;

import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.dto.entities.TestDTO;
import ua.com.zno.online.dto.entities.TestResultDTO;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.dto.statistics.SubjectStatistics;

import java.util.List;

/**
 * Created by quento on 29.03.17.
 */
public interface LoggedUserService extends UserService {

    @Transactional(readOnly = true)
    TestDTO getRandomizedTest(Long subjectId);

    @Transactional(readOnly = true)
    TestDTO getFailedQuestions();

    @Transactional(readOnly = true)
    TestDTO getFailedQuestionsBySubject(Long subjectId);

    @Transactional
    void saveFailedQuestionsResult(TestResultDTO testResultDTO) throws ZnoUserException;

    @Transactional
    List<SubjectStatistics> getStatistics();

    @Transactional
    User getAuthenticatedUser();

    @Transactional
    List<SubjectStatistics> getNotificationFailed();

}
