package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.com.zno.online.domain.Subject;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */
public interface SubjectRepository extends AbstractRepository<Subject> {

    @Query(value = "select s.name, t.id, t.name from subjects s " +
            "join tests t on s.id = t.subject_id " +
            "where s.id = ?1", nativeQuery = true)
    Subject getSubjectWithTestNames(Long subjectId);

    @Query(value = "select s.name from subjects s join tests t on s.id = t.subject_id where t.id = ?1", nativeQuery = true)
    @Deprecated
    String findSubjectNameByTestId(long testId);

    Subject findByTestsId(Long testId);

}
