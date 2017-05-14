package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.zno.online.domain.user.User;

import java.util.List;

/**
 * Created by obalitskiy on 3/31/17.
 */
@Repository
public interface UserRepository extends AbstractRepository<User> {
    User findUserByLogin(String login);

    User findUserByEmail(String email);

    @Query("select  u.email from User u join FailedQuestions fq on u.id = fq.user_id " +
            "where fq.resolved = false and u.enabled = true")
    List<String> getEmailsOfUsersWithFailedQuestions();
}
