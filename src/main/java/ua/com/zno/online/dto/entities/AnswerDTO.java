package ua.com.zno.online.dto.entities;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.dto.AbstractDTO;

/**
 * Created by quento on 26.03.17.
 */

@JsonRootName("answer")
public class AnswerDTO extends AbstractDTO {

    private Long questionId;

    private String answerText;

    private int mark;

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
