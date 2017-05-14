package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ua.com.zno.online.domain.FailedQuestion;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by quento on 11.04.17.
 */
public interface FailedQuestionRepository extends AbstractRepository<FailedQuestion> {

    @Modifying
    @Query("update FailedQuestion f set f.nextAskTime = ?1 , f.resolved = false where f.userId = ?2")
    void setNewAskDate(LocalDateTime newAskDate, Long userId);

    Set<Long> findIdByDeletedFalseAndUserId(Long userId);

    @Modifying
    @Query("update FailedQuestion f set f.resolved = true where f.userId=?1")
    void markResolved(Long id);
}
