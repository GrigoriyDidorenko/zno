package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import ua.com.zno.online.domain.Subject;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */

public class TestDTO extends AbstractDTO {

    private String name;

    private Integer duration;

    private Long subjectId;

    private List<QuestionDTO> questions;

    private String year;

    public TestDTO() {
    }

    public TestDTO(String name, List<QuestionDTO> questions) {
        this.name = name;
        this.questions = questions;
    }

    public TestDTO(String name, int duration, Long subjectId, List<QuestionDTO> questions) {
        this.name = name;
        this.duration = duration;
        this.subjectId = subjectId;
        this.questions = questions;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
