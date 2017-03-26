package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.domain.Question;
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.Test;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */

@JsonRootName("test")
public class TestDTO extends AbstractDTO {

    private String name;

    private int duration;

    private Subject subject;

    private List<QuestionDTO> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
