package ua.com.zno.online.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.EntityToDTO;
import ua.com.zno.online.DTOs.TestDTO;

/**
 * Created by quento on 26.03.17.
 */
@Service
public class DefaultLoggedUserService extends AbstractUserService implements LoggedUserService {

    @Autowired
    private EntityToDTO entityToDTO;

    @Override
    public TestDTO getRandomizedTest(Long subjectId) {
        return null;
    }

    @Override
    public void saveTestResult(TestDTO testDTO) {

    }

    public void checkTest() {
    }


}
