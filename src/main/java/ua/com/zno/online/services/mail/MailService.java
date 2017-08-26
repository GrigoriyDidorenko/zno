package ua.com.zno.online.services.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.com.zno.online.domain.mail.Mail;

/**
 * Created by quento on 28.03.17.
 */


@Service
@PropertySource("classpath:custom.properties")
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class.getName());

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freeMarkerConfiguration;


    @Value(value = "${system.email}")
    private String from;

    //could be tested : http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/

    @Async(value = "mailSendingExecutorPool")
    public void sendEmail(Mail mail) {
        try {

            MimeMessagePreparator message = mimeMessage -> {
                MimeMessageHelper messageBuilder = new MimeMessageHelper(mimeMessage);
                messageBuilder.setFrom(from);
                messageBuilder.setTo(mail.getMailTo());
                messageBuilder.setSubject(mail.getMailSubject());

                freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/templates");
                Template template = freeMarkerConfiguration.getTemplate("mail.ftl");
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

                messageBuilder.setText(content);
            };

            mailSender.send(message);
            LOG.error("Send message to {} with topic {} and content {} ", mail.getMailTo(), mail.getMailSubject(), mail.getMailContent());
        } catch (MailException e) {
            LOG.error("Failed to send message to {} with topic {} and content. Exception {} ", mail.getMailTo(), mail.getMailSubject(), mail.getMailContent(), e);
        }
    }

}

