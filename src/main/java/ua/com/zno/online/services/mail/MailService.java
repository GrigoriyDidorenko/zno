package ua.com.zno.online.services.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by quento on 28.03.17.
 */


@Service
@PropertySource("classpath:custom.properties")
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class.getName());

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${system.email}")
    private String from;

    //could be tested : http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/

    @Async(value = "mailSendingExecutorPool")
    public void sendEmail(String to, String topic, String content) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(topic);
            messageHelper.setText(content);
        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            LOG.error("Failed to send message to {} with topic {} and content. Exception {} ", to, topic, content, e);
        }
    }


}

