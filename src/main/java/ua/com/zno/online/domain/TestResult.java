package ua.com.zno.online.domain;

import ua.com.zno.online.DTOs.statistics.SubjectStatistics;
import ua.com.zno.online.DTOs.statistics.TestStatistics;
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
@NamedNativeQueries({
        @NamedNativeQuery(query = "select s.id, s.`name`, count(f.id) as failed_questions_amount  from " +
                "(select t.id as `t_id`, t.subject_id as `subject_id` from test_results ts " +
                "join tests t on t.id = ts.test_id " +
                "where ts.deleted = 0 and t.deleted = 0 and ts.user_id=?1 " +
                "group by t.subject_id, t.id) sub " +
                "join subjects s " +
                "on sub.`subject_id` = s.id " +
                "left join failed_questions f " +
                "on f.test_id = sub.`t_id` " +
                "and f.deleted = 0  and f.resolved = 0 and f.user_id=?1 " +
                "where  s.deleted = 0 " +
                "group by s.id, s.`name`",
                name = "TestResult.getSubjectStatistics", resultSetMapping = "subjectStatistics"),

        @NamedNativeQuery(query = "select t.name as testName, tr.duration as duration, tr.mark as mark , tr.failed_questions_amount as failed_questions_amount " +
                " from test_results tr " +
                " join tests t on tr.test_id = t.id " +
                " where tr.user_id = ?1 and t.subject_id =?2 and tr.deleted = 0 and t.deleted = 0",
                name = "TestResult.getTestsStatisticsBySubject", resultSetMapping = "testStatistics")
})

@SqlResultSetMappings({

        @SqlResultSetMapping(
                name = "subjectStatistics",
                classes = {
                        @ConstructorResult(
                                targetClass = SubjectStatistics.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "failed_questions_amount", type = Integer.class)
                                }
                        )}
        ),

        @SqlResultSetMapping(
                name = "testStatistics",
                classes = {
                        @ConstructorResult(
                                targetClass = TestStatistics.class,
                                columns = {
                                        @ColumnResult(name = "testName", type = String.class),
                                        @ColumnResult(name = "duration", type = Integer.class),
                                        @ColumnResult(name = "mark", type = Double.class),
                                        @ColumnResult(name = "failed_questions_amount", type = Integer.class)
                                }
                        )
                }
        )
})

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

    @Column(name = "failed_questions_amount", nullable = false)
    private Integer failedQuestionsAmount;

    public TestResult() {
    }

    public TestResult(Test test, User user, Integer duration, Double mark, LocalDateTime submissionTime, Integer failedQuestionsAmount) {
        this.test = test;
        this.user = user;
        this.duration = duration;
        this.mark = mark;
        this.submissionTime = submissionTime;
        this.failedQuestionsAmount = failedQuestionsAmount;
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

    public Integer getFailedQuestionsAmount() {
        return failedQuestionsAmount;
    }

    public void setFailedQuestionsAmount(Integer failedQuestionsAmount) {
        this.failedQuestionsAmount = failedQuestionsAmount;
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
