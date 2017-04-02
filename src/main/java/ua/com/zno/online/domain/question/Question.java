package ua.com.zno.online.domain.question;

import org.hibernate.Hibernate;
import ua.com.zno.online.domain.AbstractEntity;
import ua.com.zno.online.domain.Answer;
import ua.com.zno.online.domain.Test;

import javax.persistence.*;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */

@Entity
@Table(catalog = "zno", name = "questions")
public class Question extends AbstractEntity {

    @Column(name = "question", nullable = false, length = 500)
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private QuestionType type;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @OneToMany(mappedBy = "parentId")
    private Set<Question> subQuestions;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Optional<Long> getParentId() {
        return Optional.ofNullable(parentId);
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Question> getSubQuestions() {
        if (type == QuestionType.COMPLEX) {
            if (!Hibernate.isInitialized(this.subQuestions))
                Hibernate.initialize(this.subQuestions);

            return subQuestions;
        }

        return Collections.emptySet();
    }

    public void setSubQuestions(Set<Question> subQuestions) {
        this.subQuestions = subQuestions;
    }
}
