package ua.com.zno.online.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.zno.online.DTOs.TestDTO;
import ua.com.zno.online.DTOs.mapper.EntityToDTO;
import ua.com.zno.online.domain.Subject;
import ua.com.zno.online.domain.Test;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.repository.SubjectRepository;
import ua.com.zno.online.repository.TestRepository;
import ua.com.zno.online.util.Shuffler;

import java.util.List;
import java.util.Optional;

/**
 * Created by quento on 26.03.17.
 */
@Transactional
abstract class AbstractUserService implements UserService {

    @Autowired
    protected TestRepository testRepository;

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected EntityToDTO entityToDTO;

    @Override
    public final TestDTO getTest(Long id) throws ServerException {
        Optional<Test> test = Optional.of(testRepository.findByIdAndDeletedFalse(id));

        if (test.isPresent()) {
            TestDTO testDTO = entityToDTO.convertToDTO(test.get(), TestDTO.class);
            Shuffler.shuffle(testDTO);
            return testDTO;
        }

        throw new ServerException(String.format("Test with id %d was not found by querying from REST API", id));
    }

    @Override
    public final List<Subject> getSubjects() {
        return subjectRepository.findByDeletedFalse();
    }

    @Override
    public final List<Test> getTestsBySubject(Long subjectId) {
        return testRepository.findTestBySubjectId(subjectId);
    }
}
