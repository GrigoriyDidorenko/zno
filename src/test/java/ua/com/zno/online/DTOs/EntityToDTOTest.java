package ua.com.zno.online.DTOs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.zno.online.ZnoOnlineApplication;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.util.Constants;

import java.util.Collections;

/**
 * Created by quento on 28.03.17.
 */

@SpringBootTest(classes = ZnoOnlineApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EntityToDTOTest {

    @Autowired
    private EntityToDTO entityToDTO;

    @Test
    public void testConvertToDTO() {

        ua.com.zno.online.domain.Test test = Mockito.mock(ua.com.zno.online.domain.Test.class);

        Question question = new Question();
        question.setId(10L);
        question.setDeleted(false);
        question.setTest(test);
        question.setQuestionText("Test question");

        Answer answer = Mockito.mock(Answer.class);

        question.setAnswers(Collections.singleton(answer));
        test.setQuestions(Collections.singleton(question));

        QuestionDTO questionDTO = entityToDTO.convertToDTO(question, QuestionDTO.class);


        Assert.assertEquals(question.getId() + Constants.ID_APPENDER, questionDTO.getId().longValue());
        Assert.assertEquals(question.getQuestionText(), questionDTO.getQuestionText());
        Assert.assertEquals(question.getAnswers().size(), questionDTO.getAnswers().size());

    }
}
