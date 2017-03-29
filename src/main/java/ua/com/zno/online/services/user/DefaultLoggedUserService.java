package ua.com.zno.online.services.user;

import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.TestDTO;

/**
 * Created by quento on 26.03.17.
 */
@Service
public class DefaultLoggedUserService extends AbstractUserService implements LoggedUserService {

    @Override
    public TestDTO getRandomizedTest(Long subjectId) {
        return null;
    }
}
