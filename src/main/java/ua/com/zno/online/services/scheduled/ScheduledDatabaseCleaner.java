package ua.com.zno.online.services.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quento on 02.04.17.
 */
@Service
@PropertySource("classpath:custom.properties")
public class ScheduledDatabaseCleaner {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledDatabaseCleaner.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Value(value = "${days.before.delete.unactivated.users}")
    private Integer from;

    @Scheduled(cron = "*0 0 0 * * MON")
    private void cleanUnactivatedUsers(){
        List<User> inactiveUserIds = userRepository.findByEnabledFalseAndCreationDateBefore(LocalDateTime.now().minusDays(from));
        inactiveUserIds.forEach(inactiveUser -> userRepository.delete(inactiveUser));

        LOG.info("Deleted {} users, which didn't activate account for 7 days", inactiveUserIds.size());
    }
}
