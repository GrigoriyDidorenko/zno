package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import ua.com.zno.online.domain.TestResult;
import ua.com.zno.online.domain.user.User;

/**
 * Created by quento on 12.04.17.
 */
public interface TestResultRepository extends AbstractRepository<TestResult> {
    @Query(value = "SELECT AVG(mark) FROM test_results t WHERE t.user_id = ?1", nativeQuery = true)
    double avrgMark(long userId);

    @Query(value = "select AVG(duration) FROM test_results t WHERE t.user_id = ?1", nativeQuery = true)
    double avrgDuration(long userId);

    @Query(value = "select count(*) from failed_questions f WHERE f.user_id = ?1", nativeQuery = true)
    int failedQuestionsCount(long userId);

    @Query(value = "SELECT AVG(mark) FROM test_results tr JOIN tests t on tr.test_id = t.id WHERE ?2 in " +
            "(SELECT id FROM subjects s where s.id = t.subject_id) AND tr.user_id = ?1", nativeQuery = true)
    double avrgMarkForSubject(long userId, long subjectId);

}
