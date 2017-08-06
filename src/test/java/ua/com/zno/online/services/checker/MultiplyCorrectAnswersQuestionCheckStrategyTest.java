package ua.com.zno.online.services.checker;

import org.junit.Test;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.DTOs.UserAnswersPerQuestionDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by g.didorenko on 06.08.17.
 */
public class MultiplyCorrectAnswersQuestionCheckStrategyTest {

    private MultiplyCorrectAnswersQuestionCheckStrategy multiplyCorrectAnswersQuestionCheckStrategy = new MultiplyCorrectAnswersQuestionCheckStrategy();

    private Answer firstCorrectAnswer;
    private Answer secondCorrectAnswer;
    private Answer incorrectAnswer;
    private Set<Answer> answers;
    private Question question;

    public MultiplyCorrectAnswersQuestionCheckStrategyTest() {
        firstCorrectAnswer = new Answer();
        firstCorrectAnswer.setId(1L);
        firstCorrectAnswer.setMark(5);

        secondCorrectAnswer = new Answer();
        secondCorrectAnswer.setId(2L);
        secondCorrectAnswer.setMark(2);

        incorrectAnswer = new Answer();
        incorrectAnswer.setId(3L);
        incorrectAnswer.setMark(0);

        answers = new HashSet<>(new ArrayList<>(Arrays.asList(firstCorrectAnswer, secondCorrectAnswer)));

        question = new Question();
        question.setId(1L);
        question.setAnswers(answers);

    }


    @Test
    public void checkOneCorrectAnswer() throws Exception {
        UserAnswersPerQuestionDTO userAnswer = new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerIds(new ArrayList<>(Collections.singletonList(firstCorrectAnswer.getId())));

        int mark = multiplyCorrectAnswersQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(firstCorrectAnswer.getMark(), mark);
    }

    @Test
    public void checkMultiplyCorrectAnswer() throws Exception {
        UserAnswersPerQuestionDTO userAnswer = new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerIds(new ArrayList<>(Arrays.asList(firstCorrectAnswer.getId(), secondCorrectAnswer.getId())));

        int mark = multiplyCorrectAnswersQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(firstCorrectAnswer.getMark() + secondCorrectAnswer.getId(), mark);
    }

    @Test
    public void checkIncorrectAnswer() throws Exception {
        UserAnswersPerQuestionDTO userAnswer = new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerIds(new ArrayList<>(Collections.singletonList(incorrectAnswer.getId())));

        int mark = multiplyCorrectAnswersQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(incorrectAnswer.getMark(), mark);
    }

}