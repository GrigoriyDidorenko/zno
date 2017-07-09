package ua.com.zno.online.services.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.exceptions.ZnoUserException;
import ua.com.zno.online.repository.QuestionRepository;

import java.util.Optional;

/**
 * Created by quento on 03.04.17.
 */
@Service
public class QuestionCheckFactory {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionCheckFactory.class.getName());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OpenQuestionCheckStrategy openQuestionCheckStrategy;

    @Autowired
    private SimpleQuestionCheckStrategy simpleQuestionCheckStrategy;


    public Integer check(TestResultDTO.UserAnswerDTO userAnswerDTO) throws ZnoUserException {
        Optional<Question> question = Optional.ofNullable(questionRepository.findOne(userAnswerDTO.getQuestionId()));

        if (!question.isPresent())
            throw new ZnoUserException("Question with such id is not present, probably hacked JSON");

        switch (question.get().getType()) {
            default:
                throw new ZnoUserException(String.format("Received complex question in test result with id %d",
                        userAnswerDTO.getQuestionId()));

            case OPEN:
                return openQuestionCheckStrategy.check(userAnswerDTO, question.get());

            case SUB_QUESTION:
            case SIMPLE:
                return simpleQuestionCheckStrategy.check(userAnswerDTO, question.get());
        }
    }
}
