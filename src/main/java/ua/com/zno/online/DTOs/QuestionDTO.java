package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.domain.question.QuestionType;

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

    private QuestionType type;

    private List<QuestionDTO> subQuestions;

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

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<QuestionDTO> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(List<QuestionDTO> subQuestions) {
        this.subQuestions = subQuestions;
    }
}
