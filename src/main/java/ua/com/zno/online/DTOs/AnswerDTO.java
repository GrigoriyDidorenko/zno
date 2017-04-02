package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by quento on 26.03.17.
 */

@JsonRootName("answer")
public class AnswerDTO extends AbstractDTO {

    private QuestionDTO question;

    private String answerText;

    private int mark;

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
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
