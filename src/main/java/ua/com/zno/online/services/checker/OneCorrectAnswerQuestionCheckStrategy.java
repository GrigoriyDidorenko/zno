package ua.com.zno.online.services.checker;

import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.UserAnswersPerQuestionDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 03.04.17.
 */

@Service
class OneCorrectAnswerQuestionCheckStrategy implements Checker<UserAnswersPerQuestionDTO, Question> {

    @Override
    public Integer check(UserAnswersPerQuestionDTO dto, Question entity) throws ZnoUserException {
        return entity.getAnswers().stream()
                .filter(answer -> answer.getId().equals(dto.getAnswerIds().get(0)))
                .mapToInt(Answer::getMark)
                .sum();
    }
}
