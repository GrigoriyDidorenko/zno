package ua.com.zno.online.dto.notification;

import ua.com.zno.online.dto.AbstractDTO;

/**
 * Created by g.didorenko on 07.09.17.
 */
public class SubjectFailedQuestionAmountDTO extends AbstractDTO {

    public SubjectFailedQuestionAmountDTO(Long id, String name, int failedQuestionsAmount) {
        super(id);
        this.name = name;
        this.failedQuestionsAmount = failedQuestionsAmount;
    }

    private String name;
    private int failedQuestionsAmount;

    public String getName() {
        return name;
    }

    public int getFailedQuestionsAmount() {
        return failedQuestionsAmount;
    }
}
