package ua.com.zno.online.domain;

import io.swagger.models.auth.In;
import org.hibernate.Hibernate;
import ua.com.zno.online.domain.question.Question;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */

@Entity
@Table(catalog = "zno", name = "tests")
public class Test extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "duration", nullable = false, length = 5)
    @Min(value = 0, message = "Duration must be positive")
    private Integer duration;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    private Set<Question> questions;

    public Test(String name, Integer year, Integer duration) {
        this.name = name;
        this.year = year;
        this.duration = duration;
    }

    public Test() {
    }

    public Test(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public void addQuestions(List<Question> questions) {
        if (getQuestions() == null) {
            setQuestions(new HashSet<>());
        }
        getQuestions().addAll(questions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Test test = (Test) o;

        if (name != null ? !name.equals(test.name) : test.name != null) return false;
        if (duration != null ? !duration.equals(test.duration) : test.duration != null) return false;
        return subject != null ? subject.equals(test.subject) : test.subject == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }
}
