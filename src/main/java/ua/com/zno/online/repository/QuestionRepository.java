package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.zno.online.domain.question.Question;

import java.util.List;

/**
 * Created by quento on 02.04.17.
 */
@Repository
public interface QuestionRepository extends AbstractRepository<Question> {

    @Query(value = "select * from questions q join failed_questions f on f.question_id = q.id " +
            "where f.user_id = ?1 and f.deleted = false and f.resolved = false and f.next_ask_day < current_date",
            nativeQuery = true)
    List<Question> findAllFailedQuestions(Long userId);

    @Query(value = "select * from questions q join failed_questions f on f.question_id = q.id " +
            "where f.user_id = ?2 and f.deleted = false and f.resolved = false and f.next_ask_day < current_date and f.subject_id = ?1",
            nativeQuery = true)
    List<Question> findAnllFailedQuestionsBySubject(Long subjectId, Long userId);
}
