package ua.com.zno.online.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.com.zno.online.dto.entities.QuestionDTO;
import ua.com.zno.online.dto.entities.TestDTO;
import ua.com.zno.online.dto.entities.TestResultDTO;
import ua.com.zno.online.domain.FailedQuestion;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.domain.TestResult;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.dto.statistics.SubjectStatistics;
import ua.com.zno.online.repository.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by quento on 26.03.17.
 */
@Service
@PropertySource("classpath:custom.properties")
public class DefaultLoggedUserService extends AbstractUserService implements LoggedUserService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private FailedQuestionRepository failedQuestionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Value(value = "#{'${days.between.remind}'.split(',')}")
    private List<Long> daysBetweenRemind;

    @Override
    public TestDTO getRandomizedTest(Long subjectId) {
        return null;
    }

    @Override
    public double processTestResult(TestResultDTO testResultDTO) throws ZnoUserException {
        Map<Long, Long> markPerQuestion = super.computeMarkPerQuestion(testResultDTO);

        List<Long> failedQuestionsIds = findFailedQuestions(markPerQuestion);
        updateFailedQuestions(failedQuestionsIds, testResultDTO.getId());

        double total = super.calculateTestResult(markPerQuestion);

        testResultRepository.save(new TestResult(testRepository.findOne(testResultDTO.getId()),
                getAuthenticatedUser(), testResultDTO.getDuration(), total, LocalDateTime.now(), failedQuestionsIds.size()));

        return total;
    }

    public double processBrainstormTestResult(TestResultDTO testResultDTO) throws ZnoUserException {
        Map<Long, Long> markPerQuestion = super.computeMarkPerQuestion(testResultDTO);

        List<Long> failedQuestionsIds = findFailedQuestions(markPerQuestion);
        updateFailedQuestions(failedQuestionsIds, testResultDTO.getId());

        return super.calculateTestResult(markPerQuestion);
    }

    private List<Long> findFailedQuestions(Map<Long, Long> markPerQuestion) {
        return markPerQuestion.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void updateFailedQuestions(List<Long> failedQuestionsIds, Long testId) {
        long userId = getAuthenticatedUser().getId();

        for (Long failedQuestionId : failedQuestionsIds) {
            if (failedQuestionRepository.existsByUserIdAndQuestionId(userId, failedQuestionId)) {
                failedQuestionRepository
                        .setNewAskDate(LocalDateTime.now().plusDays(daysBetweenRemind.get(0)), userId, failedQuestionId);
            } else {
                if (testId == null || testId == -1 || testId == 0) { //Whatever
                    testId = questionRepository.findTestIdByQuestionId(failedQuestionId);
                }
                FailedQuestion newFailedQuestionToPersist = new FailedQuestion(getAuthenticatedUser(), new Test(testId), new Question(failedQuestionId),
                        false, LocalDateTime.now(), LocalDateTime.now().plusDays(daysBetweenRemind.get(0)));

                failedQuestionRepository.save(newFailedQuestionToPersist);
            }
        }
    }

    @Override
    public void saveFailedQuestionsResult(TestResultDTO testResultDTO) throws ZnoUserException {
        Map<Long, Long> questionIdWithMark = super.computeMarkPerQuestion(testResultDTO);
        long userId = getAuthenticatedUser().getId();

        questionIdWithMark.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(Map.Entry::getKey)
                .forEach(questionId -> {
                    Optional<LocalDateTime> nextAskDate = getNextAskTimeDate(questionId);

                    if (nextAskDate.isPresent())
                        failedQuestionRepository.setNewAskDate(nextAskDate.get(), userId, questionId);
                    else
                        failedQuestionRepository.markResolved(userId, questionId);
                });// Якщо він створюється тільки в updateFailedQuestions, що буде якщо пройти брейншторм, а фейл квещона не буде
    }


    @Override
    public TestDTO getFailedQuestions() {
        List<QuestionDTO> questionDTOs = questionRepository
                .findAllFailedQuestions(getAuthenticatedUser().getId()).stream()
                .map(question -> entityToDTO.convertToDTO(question, QuestionDTO.class))
                .collect(Collectors.toList());

        return new TestDTO("Failed questions", questionDTOs);
    }

    @Override
    public TestDTO getFailedQuestionsBySubject(Long subjectId) {
        List<QuestionDTO> questionDTOs = questionRepository
                .findFailedQuestionsBySubject(subjectId, getAuthenticatedUser().getId()).stream()
                .map(question -> entityToDTO.convertToDTO(question, QuestionDTO.class))
                .collect(Collectors.toList());

        String subjectName = subjectRepository.findOne(subjectId).getName();

        return new TestDTO("Failed questions by subject", subjectName, questionDTOs);
    }

    private Optional<LocalDateTime> getNextAskTimeDate(Long failedQuestionId) {
        FailedQuestion failedQuestion = failedQuestionRepository.findOne(failedQuestionId);
        int newStage = failedQuestion.incAndGetStage();

        if (daysBetweenRemind.size() > newStage)
            return Optional.of(failedQuestion.getNextAskTime().plusDays(daysBetweenRemind.get(newStage)));

        return Optional.empty();
    }

    @Override
    public List<SubjectStatistics> getStatistics() {
        long userId = getAuthenticatedUser().getId();

        return testResultRepository.getSubjectStatistics(userId).stream()
                .map(subject -> new SubjectStatistics(subject, testResultRepository.getTestsStatisticsBySubject(userId, subject.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectStatistics> getNotificationFailed() {
        long userId = getAuthenticatedUser().getId();
        return testResultRepository.getSubjectStatistics(userId);
    }

    @Override
    public User getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findUserByLogin(user.getUsername());
    }
}
