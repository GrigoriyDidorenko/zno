package ua.com.zno.online.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.QuestionDTO;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.domain.FailedQuestion;
import ua.com.zno.online.domain.TestResult;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.DTOs.statistic.SubjectStatistics;
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
        double total = super.calculateTestResult(markPerQuestion);

        this.updateFailedQuestions(markPerQuestion, testResultDTO.getId());

        testResultRepository.save(new TestResult(testRepository.findOne(testResultDTO.getId()), testResultDTO.getDuration(), total, LocalDateTime.now()));

        return total;
    }

    private void updateFailedQuestions(Map<Long, Long> questionIdWithMark, Long testId) {
        List<Long> failedQuestionsIds = questionIdWithMark.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());


        for (Long failedQuestionsId : failedQuestionsIds) {
            if (failedQuestionRepository.exists(failedQuestionsId))
                failedQuestionRepository.setNewAskDate(LocalDateTime.now().plusDays(daysBetweenRemind.get(0)),
                        getAuthenticatedUser().getId());

            else {
                FailedQuestion newFailedQuestionToPersist = new FailedQuestion(getAuthenticatedUser().getId(),
                        testId,
                        failedQuestionsId, false, LocalDateTime.now(), LocalDateTime.now().plusDays(daysBetweenRemind.get(0)));

                failedQuestionRepository.save(newFailedQuestionToPersist);
            }
        }
    }

    @Override
    public void saveFailedQuestionsResult(TestResultDTO testResultDTO) throws ZnoUserException {
        Map<Long, Long> questionIdWithMark = super.computeMarkPerQuestion(testResultDTO);

        questionIdWithMark.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(Map.Entry::getKey)
                .forEach(id -> {
                    Optional<LocalDateTime> askNextTime = this.askNextTime(id);

                    if (askNextTime.isPresent())
                        failedQuestionRepository.setNewAskDate(askNextTime.get(), id);
                    else
                        failedQuestionRepository.markResolved(id);
                });
    }


    @Override
    public TestDTO getFailedQuestions() {
        List<Question> failedQuestions = questionRepository.findAllFailedQuestions(getAuthenticatedUser().getId());
        List<QuestionDTO> questionDTOs = new ArrayList<>(failedQuestions.size());
        failedQuestions.forEach(question -> questionDTOs.add(super.entityToDTO.convertToDTO(question, QuestionDTO.class)));

        return new TestDTO("Failed questions", questionDTOs);
    }

    @Override
    public TestDTO getFailedQuestionsBySubject(Long subjectId) {
        List<Question> failedQuestions = questionRepository.findAnllFailedQuestionsBySubject(subjectId, getAuthenticatedUser().getId());
        List<QuestionDTO> questionDTOs = new ArrayList<>(failedQuestions.size());
        failedQuestions.forEach(question -> questionDTOs.add(super.entityToDTO.convertToDTO(question, QuestionDTO.class)));
        String subjectName = subjectRepository.findOne(subjectId).getName();

        return new TestDTO("Failed questions by subject", subjectName, questionDTOs); //FIXME if there is no failed q - 404
    }

    private Optional<LocalDateTime> askNextTime(Long failedQuestionId) {
        FailedQuestion failedQuestion = failedQuestionRepository.findOne(failedQuestionId);
        int newStage = failedQuestion.incAndGetStage();

        if (daysBetweenRemind.size() > newStage)
            return Optional.of(failedQuestion.getCreationDate().plusDays(daysBetweenRemind.get(newStage)));

        return Optional.empty();
    }

    @Override
    public List<SubjectStatistics> getStatistics() {
        long userId = getAuthenticatedUser().getId();

        return testResultRepository.getStatisticsForUser(userId).stream()
                .collect(Collectors.groupingBy(SubjectStatistics.TestStatistics::getSubjectName))
                .entrySet().stream()
                .map(e -> new SubjectStatistics(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    //TODO not tested because findAllByUserId throws java.io.EOFException: null (findAll does the same)
    @Override
    public Map<String, Integer> getNotificationFailed() {
        long userId = getAuthenticatedUser().getId();

        return failedQuestionRepository.findAllByUserId(userId).stream()
                .collect(Collectors.groupingBy(FailedQuestion::getTestId))
                .entrySet().stream()
                .collect(Collectors.toMap(e -> subjectRepository.findSubjectNameByTestId(e.getKey()), e -> e.getValue().size()));
    }

    @Override
    public User getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findUserByLogin(user.getUsername());
    }
}
