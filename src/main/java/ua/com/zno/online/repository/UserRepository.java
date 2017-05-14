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

    @Query(value = "select u.email from Users u join failed_questions fq on u.id = fq.user_id " +
            "where fq.resolved = false and u.enabled = true", nativeQuery = true)
    List<String> getEmailsOfUsersWithFailedQuestions();
}
