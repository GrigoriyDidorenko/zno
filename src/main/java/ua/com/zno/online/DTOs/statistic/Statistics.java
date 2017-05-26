package ua.com.zno.online.DTOs.statistic;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 13.05.2017.
 */
public class Statistics {
    private double avrgMark;

    private Map<String, Double> avrgMarkForSubject;

    private double avrgDuration;

    private int faildQuestions;

    //TODO failed questions, duration

    public Statistics(double avrgMark, Map<String, Double> avrgMarkForSubject, double avrgDuration, int faildQuestions) {
        this.avrgMark = avrgMark;
        this.avrgMarkForSubject = avrgMarkForSubject;
        this.avrgDuration = avrgDuration;
        this.faildQuestions = faildQuestions;
    }

    public Statistics() {
    }

    public double getAvrgMark() {
        return avrgMark;
    }

    public void setAvrgMark(double avrgMark) {
        this.avrgMark = avrgMark;
    }

    public Map<String, Double> getAvrgMarkForSubject() {
        return avrgMarkForSubject;
    }

    public void setAvrgMarkForSubject(Map<String, Double> avrgMarkForSubject) {
        this.avrgMarkForSubject = avrgMarkForSubject;
    }

    public double getAvrgDuration() {
        return avrgDuration;
    }

    public void setAvrgDuration(double avrgDuration) {
        this.avrgDuration = avrgDuration;
    }

    public int getFaildQuestions() {
        return faildQuestions;
    }

    public void setFaildQuestions(int faildQuestions) {
        this.faildQuestions = faildQuestions;
    }
}
