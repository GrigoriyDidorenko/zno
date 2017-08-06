package ua.com.zno.online.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public class UserAnswersPerQuestionDTO extends AbstractDTO {

        private List<Long> answerIds = Collections.emptyList();

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String answerText;

        public List<Long> getAnswerIds() {
            return answerIds;
        }

        public void setAnswerIds(List<Long> answerIds) {
            this.answerIds = answerIds;
        }

        public Optional<String> getAnswerText() {
            return Optional.ofNullable(answerText);
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }
    }
}
