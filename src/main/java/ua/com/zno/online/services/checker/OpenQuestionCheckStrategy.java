package ua.com.zno.online.services.checker;

import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 03.04.17.
 */

@Service
class OpenQuestionCheckStrategy implements Checker<TestResultDTO.UserAnswerDTO, Question> {


    @Override
    public Integer check(TestResultDTO.UserAnswerDTO dto, Question entity) throws ZnoUserException {
        for (Answer answer : entity.getAnswers()) {
            String userAnswer = dto.getAnswerText().trim();
            if (userAnswer.equalsIgnoreCase(answer.getAnswerText()))
                return answer.getMark();
        }
        return 0;
    }
}
