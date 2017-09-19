package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import ua.com.zno.online.domain.question.Question;

import java.util.List;

/**
 * Created by quento on 02.04.17.
 */
@Repository
public interface QuestionRepository extends AbstractRepository<Question> {

    @Query(value = "select * from questions q join failed_questions f on f.question_id = q.id " +
            "where f.user_id = ?1 and f.deleted = false and f.resolved = false and f.next_ask_time < current_date",
            nativeQuery = true)
    List<Question> findAllFailedQuestions(Long userId);

    @Query(value = "select * from questions q join failed_questions f on f.question_id = q.id JOIN tests t on f.test_id = t.id " +
            "where f.user_id = ?2 and f.deleted = false and f.resolved = false and f.next_ask_time < now() and t.subject_id = ?1",
            nativeQuery = true)
    List<Question> findFailedQuestionsBySubject(Long subjectId, Long userId);

    @Query(value = "select round(avg(nn.num)) INTO @myLimit FROM (SELECT COUNT(*) as num FROM questions q JOIN tests t on q.test_id = t.id join subjects s on t.subject_id = s.id where q.type = 0 and s.id = ?1 group by t.id) as nn; PREPARE STMT FROM 'select * from questions order by rand() limit ?'; EXECUTE STMT USING @myLimit",
            nativeQuery = true)
    List<Question> findRandSimpleQuestions(Long subjectId);

    @Query(value = "SELECT AVG(nn.num) FROM " +
            "(SELECT COUNT(*) as num " +
            "FROM questions q " +
            "JOIN tests t on q.test_id = t.id " +
            "join subjects s on t.subject_id = s.id " +
            "where q.type = 1 and s.id = ?1 " +
            "group by t.id) " +
            "as nn;",
            nativeQuery = true)
    int findAvrgCountOfComplexQuestions(Long subjectId);

    @Query(value = "SELECT AVG(nn.num) FROM " +
            "(SELECT COUNT(*) as num " +
            "FROM questions q " +
            "JOIN tests t on q.test_id = t.id " +
            "join subjects s on t.subject_id = s.id " +
            "where q.type = 3 and s.id = ?1 " +
            "group by t.id) " +
            "as nn;",
            nativeQuery = true)
    int findAvrgCountOfOpenQuestions(Long subjectId);

    @Query(value = "SELECT * from questions q JOIN tests t on q.test_id = t.id JOIN subjects s on t.subject_id = s.id " +
            "WHERE s.id = ?1",
            nativeQuery = true)
    List<Question> findQuestionsBySubject(Long subjectId);
}
