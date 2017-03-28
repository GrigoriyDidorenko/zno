package ua.com.zno.online.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.zno.online.domain.Test;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */
@Repository
public interface TestRepository extends AbstractRepository<Test> {

    List<Test> findTestBySubjectId(Long subjectId);
}
