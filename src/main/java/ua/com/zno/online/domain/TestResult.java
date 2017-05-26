package ua.com.zno.online.domain;

import ua.com.zno.online.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by quento on 02.04.17.
 */
@Entity
@Table(catalog = "zno", name = "test_results")
public class TestResult extends AbstractEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Test test;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Min(value = 0, message = "Duration must be positive")
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Min(value = 100, message = "Total mark must be at least 100")
    @Max(value = 200, message = "Total mark must be max 200")
    @Column(name = "mark", nullable = false)
    private Double mark;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    public TestResult() {
    }

    public TestResult(Test test, Integer duration, Double mark, LocalDateTime submissionTime) {
        this.test = test;
        this.duration = duration;
        this.mark = mark;
        this.submissionTime = submissionTime;
    }

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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
