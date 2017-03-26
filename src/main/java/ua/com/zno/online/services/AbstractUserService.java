package ua.com.zno.online.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.zno.online.DTOs.EntityToDTO;
import ua.com.zno.online.DTOs.TestDTO;
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
public abstract class AbstractUserService implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(GuestService.class.getName());

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EntityToDTO entityToDTO;

    @Override
    public final TestDTO getTest(Long id) throws ServerException {
        Optional<Test> test = Optional.of(testRepository.findOne(id));

        if (test.isPresent()) {
            TestDTO testDTO = entityToDTO.convertToDTO(test.get(), TestDTO.class);
            Shuffler.shuffle(testDTO);
            return testDTO;
        }

        LOG.error("Test with id {} was not found", id);
        throw new ServerException();
    }

    @Override
    public final List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public final List<Test> getTestsBySubject(Long subjectId) {
        return testRepository.findTestBySubjectId(subjectId);
    }
}
