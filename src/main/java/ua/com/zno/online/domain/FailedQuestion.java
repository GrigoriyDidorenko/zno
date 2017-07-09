package ua.com.zno.online.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by quento on 11.04.17.
 */
@Entity
@Table(catalog = "zno", name = "failed_questions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "user_id"}))
public class FailedQuestion extends AbstractEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

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

    public FailedQuestion(Long userId, Long subjectId, Long questionId, boolean resolved,
                          LocalDateTime creationDate, LocalDateTime nextAskTime) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.questionId = questionId;
        this.resolved = resolved;
        this.creationDate = creationDate;
        this.nextAskTime = nextAskTime;
        this.stage = 0;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FailedQuestion that = (FailedQuestion) o;

        if (resolved != that.resolved) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (subjectId != null ? !subjectId.equals(that.subjectId) : that.subjectId != null) return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        return nextAskTime != null ? nextAskTime.equals(that.nextAskTime) : that.nextAskTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (resolved ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (nextAskTime != null ? nextAskTime.hashCode() : 0);
        return result;
    }
}
