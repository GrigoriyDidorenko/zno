package ua.com.zno.online.repository;

import org.springframework.stereotype.Repository;
import ua.com.zno.online.domain.User;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Repository
public interface UserRepository extends AbstractRepository<User> {
    User findUserByLogin(String login);

}
