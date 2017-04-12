package ua.com.zno.online.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by quento on 11.04.17.
 */
@Entity
@Table(schema = "zno", catalog = "failed_questions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "user_id"}))
public class FailedQuestion extends AbstractEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "resolved", nullable = false)
    private boolean resolved;

    @Column(name = "creation_date", nullable = false)
//    @Temporal(value = TemporalType.DATE)
    private LocalDateTime creationDate;

    @Column(name = "stage", nullable = false)
    private Integer stage;


    @Column(name = "next_ask_time", nullable = false)
//    @Temporal(value = TemporalType.DATE)
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
}
