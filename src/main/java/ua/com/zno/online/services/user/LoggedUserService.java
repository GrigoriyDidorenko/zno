package ua.com.zno.online.services.user;

import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.DTOs.statistic.Statistics;

/**
 * Created by quento on 29.03.17.
 */
public interface LoggedUserService extends UserService {

    @Transactional(readOnly = true)
    TestDTO getRandomizedTest(Long subjectId);

    @Transactional
    void saveTestResult(TestResultDTO testResultDTO) throws ZnoUserException;

    @Transactional(readOnly = true)
    TestDTO getFailedQuestions();

    @Transactional(readOnly = true)
    TestDTO getFailedQuestionsBySubject(Long subjectId);

    @Transactional
    void saveFailedQuestionsResult(TestResultDTO testResultDTO) throws ZnoUserException;

    @Transactional
    Statistics getStatistics();

    User getAuthenticatedUser();

    //get user stat

}
