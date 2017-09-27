package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.com.zno.online.domain.FailedQuestion;
import ua.com.zno.online.domain.question.Question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by quento on 11.04.17.
 */
public interface FailedQuestionRepository extends AbstractRepository<FailedQuestion> {

    @Modifying
    @Query("update FailedQuestion f set f.nextAskTime = ?1 , f.resolved = false, f.stage = 0 where f.user.id = ?2 and f.question.id=?3")
    void setNewAskDate(LocalDateTime newAskDate, Long userId, Long questionId);

    @Modifying
    @Query("update FailedQuestion f set f.resolved = true where f.user.id=?1 and f.question.id=?2")
    void markResolved(Long userId, Long questionId);

    boolean existsByUserIdAndQuestionId(long userId, long questionId);
}
