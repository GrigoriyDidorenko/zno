package ua.com.zno.online.dto.entities;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.dto.AbstractDTO;
import ua.com.zno.online.domain.question.QuestionType;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */

@JsonRootName("question")
public class QuestionDTO extends AbstractDTO {

    private Integer position;

    private String questionText;

    private Long testId;

    private List<AnswerDTO> answers;

    private QuestionType type;

    private List<QuestionDTO> subQuestions;

    private Long parentId;

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
