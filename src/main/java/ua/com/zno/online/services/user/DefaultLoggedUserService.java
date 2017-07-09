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
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.TestResult;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.exceptions.UserException;
import ua.com.zno.online.DTOs.statistic.Statistics;
import ua.com.zno.online.repository.*;
import ua.com.zno.online.services.checker.QuestionCheckFactory;

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
    private QuestionCheckFactory questionCheckFactory;

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
    public void saveTestResult(TestResultDTO testResultDTO) throws UserException {
        User user = getAuthenticatedUser();

        Map<Long, Long> questionIdWithMark = checkTest(testResultDTO);
        //get 0 mark question ids
        //get total


        double total = questionIdWithMark.values().stream().mapToDouble(Long::doubleValue).sum() + 100;
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
                        testRepository.findOne(testResultDTO.getTestId()).getSubject().getId(),
                        failedQuestionsId, false, LocalDateTime.now(), LocalDateTime.now().plusDays(daysBetweenRemind.get(0)));

                failedQuestionRepository.save(newFailedQuestionToPersist);
            }
        }


        testResultRepository.save(new TestResult(testRepository.findOne(testResultDTO.getTestId()), testResultDTO.getDuration(), total, LocalDateTime.now()));
    }

    @Override
    public void saveFailedQuestionsResult(TestResultDTO testResultDTO) throws UserException {
        Map<Long, Long> questionIdWithMark = checkTest(testResultDTO);

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

    private Map<Long, Long> checkTest(TestResultDTO testResultDTO) throws UserException {
        Map<Long, Long> questionIdWithMark = new HashMap<>();

        for (TestResultDTO.UserAnswerDTO userAnswer : testResultDTO.getUserAnswerDTO()) {
            long mark = questionCheckFactory.check(userAnswer);
            questionIdWithMark.put(userAnswer.getQuestionId(), mark);
        }

        return questionIdWithMark;

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

        return new TestDTO("Failed questions by subject", questionDTOs);
    }

    private Optional<LocalDateTime> askNextTime(Long failedQuestionId) {
        FailedQuestion failedQuestion = failedQuestionRepository.findOne(failedQuestionId);
        int newStage = failedQuestion.incAndGetStage();

        if (daysBetweenRemind.size() > newStage)
            return Optional.of(failedQuestion.getCreationDate().plusDays(daysBetweenRemind.get(newStage)));

        return Optional.empty();
    }

    @Override
    public Statistics getStatistics() {
        long userId = getAuthenticatedUser().getId();

        List<Subject> subjects = subjectRepository.findAll();

        Map<String, Double> avrgMarkForSubject = new HashMap<>();

        for (Subject subject : subjects){
            avrgMarkForSubject.put(subject.getName(), testResultRepository.avrgMarkForSubject(userId, subject.getId()));
        }

        return new Statistics(testResultRepository.avrgMark(userId),
                avrgMarkForSubject,
                testResultRepository.avrgDuration(userId),
                testResultRepository.failedQuestionsCount(userId));
    }

    @Override
    public User getAuthenticatedUser() { //TODO maybe create getAuthenticatedUserId()
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return (User) userRepository.findUserByLogin(user.getUsername());
    }

    public static void main(String[] args) {
        Integer i1 = 10;
        Integer i2 = 10;

        Double d1 = 10.06;
        Double d2 = 10.06;

        Float f1 = 0.8f;
        Float f2 = 0.8f;

        String str1 = "Bar";
        String str2 = "Bar";

        System.out.println(i1 == i2); //true
        System.out.println(d1 == d2); //false ?????????
        System.out.println(f1 == f2); //true
        System.out.println(str1 == str2); //true
    }
}
