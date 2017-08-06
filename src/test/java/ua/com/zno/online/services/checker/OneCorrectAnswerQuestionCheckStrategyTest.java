package ua.com.zno.online.services.checker;

import org.junit.Test;
import ua.com.zno.online.DTOs.TestResultDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by g.didorenko on 06.08.17.
 */
public class OneCorrectAnswerQuestionCheckStrategyTest {

    private OneCorrectAnswerQuestionCheckStrategy oneCorrectAnswerQuestionCheckStrategy = new OneCorrectAnswerQuestionCheckStrategy();

    private Answer correctAnswer;
    private Answer incorrectAnswer;
    private Set<Answer> answers;
    private Question question;

    public OneCorrectAnswerQuestionCheckStrategyTest() {
        correctAnswer = new Answer();
        correctAnswer.setId(1L);
        correctAnswer.setMark(5);

        incorrectAnswer = new Answer();
        incorrectAnswer.setId(2L);
        incorrectAnswer.setMark(0);

        answers = new HashSet<>(new ArrayList<>(Collections.singletonList(correctAnswer)));

        question = new Question();
        question.setId(1L);
        question.setAnswers(answers);

    }

    @Test
    public void checkCorrectAnswer() throws Exception {
        TestResultDTO.UserAnswersPerQuestionDTO userAnswer = new TestResultDTO().new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerIds(new ArrayList<>(Collections.singletonList(correctAnswer.getId())));

        int mark = oneCorrectAnswerQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(correctAnswer.getMark(), mark);
    }

    @Test
    public void checkIncorrectAnswer() throws Exception {
        TestResultDTO.UserAnswersPerQuestionDTO userAnswer = new TestResultDTO().new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerIds(new ArrayList<>(Collections.singletonList(incorrectAnswer.getId())));

        int mark = oneCorrectAnswerQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(incorrectAnswer.getMark(), mark);
    }

}