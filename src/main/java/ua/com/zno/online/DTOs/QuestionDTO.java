package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.Test;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */

@JsonRootName("question")
public class QuestionDTO extends AbstractDTO {

    private String questionText;

    private Test test;

    private List<AnswerDTO> answers;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
