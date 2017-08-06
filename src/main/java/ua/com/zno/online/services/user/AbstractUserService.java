package ua.com.zno.online.services.user;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.domain.question.QuestionType;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.repository.QuestionRepository;
import ua.com.zno.online.repository.SubjectRepository;
import ua.com.zno.online.repository.TestRepository;
import ua.com.zno.online.util.Shuffler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by quento on 26.03.17.
 */
@Transactional
abstract class AbstractUserService implements UserService {

    @Autowired
    protected TestRepository testRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected EntityToDTO entityToDTO;

    @Autowired
    protected QuestionRepository questionRepository;

    @Override
    public final TestDTO getTest(Long id) throws ZnoServerException {
        Optional<Test> test = Optional.of(testRepository.findByIdAndDeletedFalse(id));

        if (test.isPresent()) {
            Hibernate.initialize(test.get().getQuestions());
            TestDTO testDTO = entityToDTO.convertToDTO(test.get(), TestDTO.class);
            Shuffler.shuffle(testDTO);//FIXME can not shuffle like that
            return testDTO;
        }

        throw new ZnoServerException(String.format("Test with id %d was not found by querying from REST API", id));
    }

    @Override
    public final List<Subject> getSubjects() {
        return subjectRepository.findByDeletedFalse();
    }

    @Override
    public final List<Test> getTestsBySubject(Long subjectId) {
        return testRepository.findTestsBySubjectId(subjectId);
    }

    @Override
    public TestDTO getShuffledTestBySubject(Long subjectId) throws ZnoUserException {
        List<Test> tests = testRepository.findTestsBySubjectId(subjectId);
        if (tests.isEmpty()) throw new ZnoUserException("there is no such subjectId: " + subjectId);

        List<Question> questions = new ArrayList<>();

        tests.forEach(test -> questions.addAll(test.getQuestions()));
        int avrgNumOfSimpleQuestions = getAvrgNumberOfQuestionsOfCertainType(questions, QuestionType.QUESTION_WITH_ONE_CORRECT_ANSWER, tests.size());
        int avrgNumOfComplexQuestions = getAvrgNumberOfQuestionsOfCertainType(questions, QuestionType.QUESTION_WITH_SUB_QUESTIONS, tests.size());
        int avrgNumOfOpenQuestions = getAvrgNumberOfQuestionsOfCertainType(questions, QuestionType.OPEN, tests.size());

        Test test = new Test("Brainstorm", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), 100); //FIXME duration
        test.addQuestions(getShuffledLimitedQuestions(questions, QuestionType.QUESTION_WITH_ONE_CORRECT_ANSWER, avrgNumOfSimpleQuestions));
        test.addQuestions(getShuffledLimitedQuestions(questions, QuestionType.QUESTION_WITH_SUB_QUESTIONS, avrgNumOfComplexQuestions));
        test.addQuestions(getShuffledLimitedQuestions(questions, QuestionType.OPEN, avrgNumOfOpenQuestions));
        test.setId(-1L);
        test.setSubject(subjectRepository.findOne(subjectId));


        return entityToDTO.convertToDTO(test, TestDTO.class);
    }

    private List<Question> getShuffledLimitedQuestions(List<Question> questions, QuestionType type, int num){
        questions = new ArrayList<>(questions
                .stream()
                .filter(q -> q.getType().equals(type)).collect(Collectors.toList()));
        Collections.shuffle(questions);
        return questions.stream().limit(num).collect(Collectors.toList());
    }

    private int getAvrgNumberOfQuestionsOfCertainType(List<Question> questions, QuestionType questionType, int size){
        return (int)Math.ceil((double) questions
                .stream()
                .filter(q -> q.getType().equals(questionType))
                .count() / size);
    }
}
