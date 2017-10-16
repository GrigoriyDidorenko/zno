package ua.com.zno.online.dto.statistics;

import ua.com.zno.online.dto.AbstractDTO;

import java.util.List;

/**
 * Created by mac on 13.05.2017.
 */
public class SubjectStatistics extends AbstractDTO {
    private String name;
    private double avgDuration;
    private double avgMark;
    private int failedQuestionsAmount;
    private List<TestStatistics> testStatistics;

    public SubjectStatistics(Long subjectId, String name, int failedQuestionsAmount) {
        super(subjectId);
        this.name = name;
        this.failedQuestionsAmount = failedQuestionsAmount;
    }

    public SubjectStatistics(SubjectStatistics instance, List<TestStatistics> testStatistics) {
        this(instance.getId(), instance.getName(), instance.getFailedQuestionsAmount());
        this.testStatistics = testStatistics;
        this.avgDuration = testStatistics.stream().mapToDouble(TestStatistics::getDuration).average().orElse(0);
        this.avgMark = testStatistics.stream().mapToDouble(TestStatistics::getMark).average().orElse(0);
    }

    public String getName() {
        return name;
    }

    public double getAvgDuration() {
        return avgDuration;
    }

    public double getAvgMark() {
        return avgMark;
    }

    public int getFailedQuestionsAmount() {
        return failedQuestionsAmount;
    }

    public List<TestStatistics> getTestStatistics() {
        return testStatistics;
    }
}