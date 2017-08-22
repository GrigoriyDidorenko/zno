package ua.com.zno.online.DTOs.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Created by mac on 13.05.2017.
 */
public class SubjectStatistics {
    private String subjectName;
    private double avrgDuration;
    private double avrgMark;
    private int numOfFailedQuestions;
    private List<TestStatistics> testStatistics;

    public SubjectStatistics(String subjectName, List<TestStatistics> testStatistics) {
        this.testStatistics = testStatistics;

        this.subjectName = subjectName;
        this.avrgDuration = testStatistics.stream().mapToDouble(e -> e.duration).average().orElse(0);
        this.avrgMark = testStatistics.stream().mapToDouble(e -> e.mark).average().orElse(0);
        this.numOfFailedQuestions = testStatistics.stream().mapToInt(e -> e.numOfFailedQuestions).sum();
    }

    public SubjectStatistics() {
    }

    public static class TestStatistics {
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String subjectName;
        private String testName;
        private int duration;
        private double mark;
        private int numOfFailedQuestions;

        public TestStatistics(String subjectName, String testName, int duration, double mark, int numOfFailedQuestions) {
            this.subjectName = subjectName;
            this.testName = testName;
            this.duration = duration;
            this.mark = mark;
            this.numOfFailedQuestions = numOfFailedQuestions;
        }

        public TestStatistics() {
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public double getMark() {
            return mark;
        }

        public void setMark(double mark) {
            this.mark = mark;
        }

        public int getNumOfFailedQuestions() {
            return numOfFailedQuestions;
        }

        public void setNumOfFailedQuestions(int numOfFailedQuestions) {
            this.numOfFailedQuestions = numOfFailedQuestions;
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