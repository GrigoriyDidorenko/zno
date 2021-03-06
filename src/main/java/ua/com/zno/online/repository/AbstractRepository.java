package ua.com.zno.online.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ua.com.zno.online.domain.Subject;

import java.util.List;

/**
 * Created by quento on 28.03.17.
 */

@NoRepositoryBean
public interface AbstractRepository<T> extends CrudRepository<T, Long> {

    @Override
    List<T> findAll();

    List<T> findByDeletedFalse();

    T findByIdAndDeletedFalse(Long id);
}
