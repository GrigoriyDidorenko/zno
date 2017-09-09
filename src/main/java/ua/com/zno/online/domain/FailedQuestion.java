package ua.com.zno.online.domain;

import ua.com.zno.online.domain.question.Question;
import ua.com.zno.online.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by quento on 11.04.17.
 */
@Entity
@Table(catalog = "zno", name = "failed_questions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "user_id"}))
public class FailedQuestion extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
    private Test test;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private Question question;

    /*
    *
    * mark question as resolved after all stages are answered
    *
    * */
    @Column(name = "resolved", nullable = false)
    private boolean resolved;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;


    /*
    *
    * number of stages to ask question again, depends on property ${days.between.remind} size
    *
    * */
    @Column(name = "stage", nullable = false)
    private Integer stage;

    /*
    *
    * based on property ${days.between.remind} selects next date to ask
    *
    * */
    @Column(name = "next_ask_time", nullable = false)
    private LocalDateTime nextAskTime;

    public FailedQuestion(User user, Test test, Question question, boolean resolved,
                          LocalDateTime creationDate, LocalDateTime nextAskTime) {
        this.user = user;
        this.test = test;
        this.question = question;
        this.resolved = resolved;
        this.creationDate = creationDate;
        this.nextAskTime = nextAskTime;
        this.stage = 0;
    }

    public FailedQuestion() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer incAndGetStage() {
        return ++stage;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public LocalDateTime getNextAskTime() {
        return nextAskTime;
    }

    public void setNextAskTime(LocalDateTime nextAskTime) {
        this.nextAskTime = nextAskTime;
    }

}
