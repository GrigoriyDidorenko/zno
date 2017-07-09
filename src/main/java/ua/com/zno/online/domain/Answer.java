package ua.com.zno.online.domain;

import ua.com.zno.online.domain.question.Question;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created by quento on 26.03.17.
 */

@Entity
@Table(catalog = "zno", name = "answers")
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Min(value = 0, message = "Mark must be positive")
    @Column(name = "mark", nullable = false)
    private int mark;

    @Column(name = "image")
    private byte[] image;

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Answer answer = (Answer) o;

        if (mark != answer.mark) return false;
        return answerText != null ? answerText.equals(answer.answerText) : answer.answerText == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + mark;
        return result;
    }
}
