package ua.com.zno.online.services.user;

import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.DTOs.TestDTO;

/**
 * Created by quento on 29.03.17.
 */
public interface LoggedUserService extends UserService {

    @Transactional(readOnly = true)
    TestDTO getRandomizedTest(Long subjectId);

    @Transactional
    void saveTestResult(TestDTO testDTO);

    //get user stat

}
