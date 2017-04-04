package ua.com.zno.online.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.repository.SubjectRepository;
import ua.com.zno.online.repository.TestRepository;

/**
 * Created by quento on 26.03.17.
 */
@Service
public class DefaultLoggedUserService extends AbstractUserService implements LoggedUserService {

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
