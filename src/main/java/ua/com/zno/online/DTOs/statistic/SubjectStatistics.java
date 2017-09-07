package ua.com.zno.online.DTOs.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.zno.online.DTOs.AbstractDTO;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Created by mac on 13.05.2017.
 */
public class SubjectStatistics  extends AbstractDTO{
    private String subjectName;
    private double avrgDuration;
    private double avrgMark;
    private int numOfFailedQuestions;
    private List<TestStatistics> testStatistics;

    public SubjectStatistics(Long subjectId, String subjectName, List<TestStatistics> testStatistics) {
        super.setId(subjectId);
        this.testStatistics = testStatistics;
        this.subjectName = subjectName;
        this.avrgDuration = testStatistics.stream().mapToDouble(e -> e.duration).average().orElse(0);
        this.avrgMark = testStatistics.stream().mapToDouble(e -> e.mark).average().orElse(0);
        this.numOfFailedQuestions = testStatistics.stream().mapToInt(e -> e.numOfFailedQuestions).sum();
    }

    public SubjectStatistics() {
    }

    public static class TestStatistics {
        @JsonIgnore
        private Long subjectId;
        private String testName;
        private int duration;
        private double mark;
        private int numOfFailedQuestions;

        public TestStatistics(Long subjectId, String testName, int duration, double mark, int numOfFailedQuestions) {
            this.subjectId = subjectId;
            this.testName = testName;
            this.duration = duration;
            this.mark = mark;
            this.numOfFailedQuestions = numOfFailedQuestions;
        }

        public TestStatistics() {
        }

        public Long getSubjectId() {
            return subjectId;
        }

        public String getTestName() {
            return testName;
        }

        public int getDuration() {
            return duration;
        }

        public double getMark() {
            return mark;
        }

        public int getNumOfFailedQuestions() {
            return numOfFailedQuestions;
        }
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getAvrgDuration() {
        return avrgDuration;
    }

    public void setAvrgDuration(double avrgDuration) {
        this.avrgDuration = avrgDuration;
    }

    public double getAvrgMark() {
        return avrgMark;
    }

    public void setAvrgMark(double avrgMark) {
        this.avrgMark = avrgMark;
    }

    public int getNumOfFailedQuestions() {
        return numOfFailedQuestions;
    }

    public void setNumOfFailedQuestions(int numOfFailedQuestions) {
        this.numOfFailedQuestions = numOfFailedQuestions;
    }

    public List<TestStatistics> getTestStatistics() {
        return testStatistics;
    }

    public void setTestStatistics(List<TestStatistics> testStatistics) {
        this.testStatistics = testStatistics;
    }
}