package ua.com.zno.online.services.user;

import org.springframework.stereotype.Service;
import ua.com.zno.online.dto.entities.TestResultDTO;
import ua.com.zno.online.exceptions.ZnoUserException;

import java.util.Map;

/**
 * Created by quento on 26.03.17.
 */

@Service
public class GuestService extends AbstractUserService implements UserService {


    @Override
    public double processTestResult(TestResultDTO testResultDTO) throws ZnoUserException {
        Map<Long, Long> markPerQuestion = super.computeMarkPerQuestion(testResultDTO);
        return super.calculateTestResult(markPerQuestion);
    }
}
