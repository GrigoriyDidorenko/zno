package ua.com.zno.online.domain;

import ua.com.zno.online.DTOs.statistic.SubjectStatistics;
import ua.com.zno.online.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Created by quento on 02.04.17.
 */
@Entity
@Table(catalog = "zno", name = "test_results")
@NamedNativeQuery(query = "select s.id as subjectId, t.name as testName, tr.duration as duration, tr.mark as mark, tmp.count as numOfFailedQuestions" +
        " from test_results tr" +
        " join tests t on tr.test_id = t.id" +
        " join subjects s on t.subject_id = s.id" +
        " left join (select test_id, count(*) as count from failed_questions fq group by test_id) as tmp on t.id = tmp.test_id" +
        " where tr.user_id = ?1", name = "TestResult.getStatisticsForUser", resultSetMapping = "statistics")

@SqlResultSetMapping(
        name = "statistics",
        classes = {
                @ConstructorResult(
                        targetClass = SubjectStatistics.TestStatistics.class,
                        columns = {
                                @ColumnResult(name = "subjectId", type = Long.class),
                                @ColumnResult(name = "testName", type = String.class),
                                @ColumnResult(name = "duration", type = Integer.class),
                                @ColumnResult(name = "mark", type = Double.class),
                                @ColumnResult(name = "numOfFailedQuestions", type = Integer.class)
                        }
                )
        }
)
public class TestResult extends AbstractEntity {

    @ManyToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Test test;

    @ManyToOne(optional = false)
    @PrimaryKeyJoinColumn
    private User user;

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

    public TestResult(Test test, User user, Integer duration, Double mark, LocalDateTime submissionTime) {
        this.test = test;
        this.user = user;
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
