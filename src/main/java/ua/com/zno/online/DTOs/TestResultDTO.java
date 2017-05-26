package ua.com.zno.online.DTOs;

import ua.com.zno.online.domain.question.QuestionType;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by quento on 09.04.17.
 */
public class TestResultDTO extends AbstractDTO { // FIXME added test and user to testResult

    @NotNull
    private Long testId;

    @NotNull
    private Integer duration;

    private List<UserAnswerDTO> userAnswerDTO;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<UserAnswerDTO> getUserAnswerDTO() {
        return userAnswerDTO;
    }

    public void setUserAnswerDTO(List<UserAnswerDTO> userAnswerDTO) {
        this.userAnswerDTO = userAnswerDTO;
    }

    public class UserAnswerDTO extends AbstractDTO {
        private Long questionId;
        private Long answerId;
        private String answerText;

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public Long getAnswerId() {
            return answerId;
        }

        public void setAnswerId(Long answerId) {
            this.answerId = answerId;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }
    }
}
