package ua.com.zno.online.util;

import ua.com.zno.online.dto.AbstractDTO;
import ua.com.zno.online.dto.entities.TestDTO;

import java.util.*;

/**
 * Created by quento on 26.03.17.
 */
public final class Shuffler {

    private Shuffler() {
    }

    public static void shuffleAnswers(TestDTO test) {
        test.getQuestions().forEach(question -> shuffle(question.getAnswers()));
    }

    private static void shuffle(List<? extends AbstractDTO> entities) {
        Collections.shuffle(entities);
    }
}
