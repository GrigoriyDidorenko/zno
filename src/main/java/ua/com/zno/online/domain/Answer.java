package ua.com.zno.online.domain;

import javax.persistence.*;

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

    @Column(name = "mark", nullable = false)
    private int mark;

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
}
