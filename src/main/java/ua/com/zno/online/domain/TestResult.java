package ua.com.zno.online.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Min(value = 0, message = "Duration must be positive")
    @Column(name = "duration")
    private Integer duration;

    @Min(value = 100, message = "Total mark must be at least 100")
    @Max(value = 200, message = "Total mark must be max 200")
    @Column(name = "mark", nullable = false)
    private Double mark;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    public TestResult() {
    }

    public TestResult(Integer duration, Double mark, LocalDateTime submissionTime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TestResult that = (TestResult) o;

        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        return submissionTime != null ? submissionTime.equals(that.submissionTime) : that.submissionTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (submissionTime != null ? submissionTime.hashCode() : 0);
        return result;
    }
}
