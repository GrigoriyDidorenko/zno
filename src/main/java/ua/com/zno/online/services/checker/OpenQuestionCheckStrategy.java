package ua.com.zno.online.services.checker;

import org.springframework.stereotype.Service;
import ua.com.zno.online.dto.user.response.UserAnswersPerQuestionDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 03.04.17.
 */

@Service
class OpenQuestionCheckStrategy implements Checker<UserAnswersPerQuestionDTO, Question> {


    @Override
    public Integer check(UserAnswersPerQuestionDTO dto, Question entity) throws ZnoUserException {
        if (dto.getAnswerText().isPresent()) {
            String userAnswer = dto.getAnswerText().get().trim();

            for (Answer answer : entity.getAnswers()) {
                if (userAnswer.equalsIgnoreCase(answer.getAnswerText()))
                    return answer.getMark();
            }
        }
        return 0;
    }
}
