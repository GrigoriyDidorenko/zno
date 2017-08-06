package ua.com.zno.online.DTOs;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by quento on 09.04.17.
 */
public class TestResultDTO extends AbstractDTO { // FIXME added test and user to testResult

    @Min(value = 0)
    private Integer duration;

    private List<UserAnswersPerQuestionDTO> userAnswersPerQuestionDTO;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<UserAnswersPerQuestionDTO> getUserAnswersPerQuestionDTO() {
        return userAnswersPerQuestionDTO;
    }

    public void setUserAnswersPerQuestionDTO(List<UserAnswersPerQuestionDTO> userAnswersPerQuestionDTO) {
        this.userAnswersPerQuestionDTO = userAnswersPerQuestionDTO;
    }

}
