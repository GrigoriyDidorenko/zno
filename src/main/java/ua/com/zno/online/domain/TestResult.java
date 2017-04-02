package ua.com.zno.online.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by quento on 02.04.17.
 */
@Entity
@Table(catalog = "zno", name = "test_results")
public class TestResult extends AbstractEntity {

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "mark", nullable = false)
    private Double mark;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    @OneToMany(mappedBy = "testResult")
    private Set<UserAnswer> userAnswers;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
