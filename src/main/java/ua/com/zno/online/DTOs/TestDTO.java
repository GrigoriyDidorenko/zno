package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quento on 26.03.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDTO extends AbstractDTO {

    private String name;

    private Integer duration;

    private Long subjectId; //TODO Do we need it?

    private List<QuestionDTO> questions;

    private Integer year;

    private String subjectName;

    public TestDTO() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public TestDTO(String name) {
        this.name = name;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public TestDTO(String name, List<QuestionDTO> questions) {
        this.name = name;
        this.questions = questions;
    }

    public TestDTO(String name, String subjectName, List<QuestionDTO> questions) {
        this.name = name;
        this.subjectName = subjectName;
        this.questions = questions;
    }

    public TestDTO(String name, int duration, Long subjectId, List<QuestionDTO> questions) {
        this.name = name;
        this.duration = duration;
        this.subjectId = subjectId;
        this.questions = questions;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
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
        //todo bullshit
        return questions.stream().filter(questionDTO -> questionDTO.getParentId() == null).collect(Collectors.toList());
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
