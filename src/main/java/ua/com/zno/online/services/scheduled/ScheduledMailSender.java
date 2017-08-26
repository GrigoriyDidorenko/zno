package ua.com.zno.online.services.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.zno.online.domain.mail.Mail;
import ua.com.zno.online.domain.user.User;
import ua.com.zno.online.repository.UserRepository;
import ua.com.zno.online.services.mail.MailService;

import java.util.List;

/**
 * Created by quento on 14.05.17.
 */

@Service
public class ScheduledMailSender {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledMailSender.class.getName());

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * FRI")
    public void sendEmailToUsersWithFailedQuestions() {
        //TODO: Grigoriy check query
        List<String> emails = userRepository.getEmailsOfUsersWithFailedQuestions();
        LOG.debug("Sending email to users, which has failed questions, num of users {}", emails.size());

        emails.forEach(email -> mailService.sendEmail(new Mail(email, "topic", "content")));
    }
}
