package ua.com.zno.online.DTOs.statistics;

/**
 * Created by g.didorenko on 16.09.17.
 */
public class TestStatistics {
    private String testName;
    private int duration;
    private double mark;
    private int numOfFailedQuestions;

    public TestStatistics(String testName, int duration, double mark, int numOfFailedQuestions) {
        this.testName = testName;
        this.duration = duration;
        this.mark = mark;
        this.numOfFailedQuestions = numOfFailedQuestions;
    }

    public TestStatistics() {
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
