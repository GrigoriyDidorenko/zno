package ua.com.zno.online.services;

import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class GuestService extends AbstractUserService implements UserService {

}
