package ua.com.zno.online.services.checker;

import org.junit.Test;
import ua.com.zno.online.dto.user.response.UserAnswersPerQuestionDTO;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by g.didorenko on 06.08.17.
 */


public class OpenQuestionCheckStrategyTest {

    private OpenQuestionCheckStrategy openQuestionCheckStrategy = new OpenQuestionCheckStrategy();

    private Answer correctAnswer;
    private Answer incorrectAnswer;
    private Set<Answer> answers;
    private Question question;


    public OpenQuestionCheckStrategyTest() {
        correctAnswer = new Answer();
        correctAnswer.setId(1L);
        correctAnswer.setMark(5);
        correctAnswer.setAnswerText("correct");

        incorrectAnswer = new Answer();
        incorrectAnswer.setId(2L);
        incorrectAnswer.setMark(0);
        incorrectAnswer.setAnswerText("incorrect");

        answers = new HashSet<>(new ArrayList<>(Arrays.asList(correctAnswer, incorrectAnswer)));

        question = new Question();
        question.setId(1L);
        question.setAnswers(answers);

    }

    @Test
    public void checkCorrectAnswer() throws Exception {
        UserAnswersPerQuestionDTO userAnswer = new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerText("  " + correctAnswer.getAnswerText().toUpperCase() + "   ");

        int mark = openQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(correctAnswer.getMark(), mark);
    }

    @Test
    public void checkIncorrectAnswer() throws Exception {
        UserAnswersPerQuestionDTO userAnswer = new UserAnswersPerQuestionDTO();
        userAnswer.setAnswerText("  " + incorrectAnswer.getAnswerText().toUpperCase() + "   ±231");

        int mark = openQuestionCheckStrategy.check(userAnswer, question);

        assertEquals(incorrectAnswer.getMark(), mark);
    }

}