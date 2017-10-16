package ua.com.zno.online.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.zno.online.dto.AbstractDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by g.didorenko on 06.08.17.
 */
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
