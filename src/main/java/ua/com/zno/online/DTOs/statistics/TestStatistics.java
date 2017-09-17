package ua.com.zno.online.DTOs.statistics;

/**
 * Created by g.didorenko on 16.09.17.
 */
public class TestStatistics {
    private String name;
    private int duration;
    private double mark;
    private int failedQuestionsAmount;

    public TestStatistics(String name, int duration, double mark, int failedQuestionsAmount) {
        this.name = name;
        this.duration = duration;
        this.mark = mark;
        this.failedQuestionsAmount = failedQuestionsAmount;
    }

    public TestStatistics() {
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public double getMark() {
        return mark;
    }

    public int getFailedQuestionsAmount() {
        return failedQuestionsAmount;
    }
}
