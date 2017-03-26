package ua.com.zno.online.util;

import ua.com.zno.online.DTOs.AbstractDTO;
import ua.com.zno.online.DTOs.TestDTO;

import java.util.*;

/**
 * Created by quento on 26.03.17.
 */
public final class Shuffler {

    private Shuffler() {

    }

    public static TestDTO shuffle(TestDTO test) {
        test.getQuestions().forEach(question -> shuffle(question.getAnswers()));
        shuffle(test.getQuestions());
        return test;
    }

    private static void shuffle(List<? extends AbstractDTO> entities) {
        Collections.shuffle(entities);
    }
}
