package ua.com.zno.online.repository;

import org.springframework.data.repository.CrudRepository;
import ua.com.zno.online.domain.Subject;

import java.util.List;

/**
 * Created by quento on 26.03.17.
 */
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    @Override
    List<Subject> findAll();
}
