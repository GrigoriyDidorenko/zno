package ua.com.zno.online.services.question.checker;

import org.springframework.stereotype.Service;
import ua.com.zno.online.dto.user.response.UserAnswersPerQuestionDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by g.didorenko on 06.08.17.
 */
@Service
public class MultiplyCorrectAnswersQuestionCheckStrategy implements QuestionChecker<UserAnswersPerQuestionDTO, Question> {


    @Override
    public Integer check(UserAnswersPerQuestionDTO dto, Question entity) throws ZnoUserException {
        return entity.getAnswers().stream()
                .filter(answer -> dto.getAnswerIds().contains(answer.getId()))
                .map(Answer::getMark)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
