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

    public static final String TEMPLATE_PASSWORD_RECOVERING = "change_password.ftl";
    public static final String TEMPLATE_CONFIRM_REGISTRATION = "registration.ftl";
    public static final String TEMPLATE_WELCOME_AFTER_REGISTRATION = "welcome.ftl";
    public static final String TEMPLATE_WEEKLY_NEWS = "week-mail.ftl";

    @Value(value = "${system.email}")
    private String from;

    private final JavaMailSender mailSender;
    private final Configuration freeMarkerConfiguration;


    @Autowired
    public MailService(JavaMailSender mailSender, Configuration freeMarkerConfiguration) {
        this.mailSender = mailSender;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

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
                Template template = freeMarkerConfiguration.getTemplate(mail.getEmailTemplate());
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

                messageBuilder.setText(content, true);
            };

            mailSender.send(message);
            LOG.info("Send message to {} with topic {} and content {} ", mail.getMailTo(), mail.getMailSubject(), mail.getMailContent());
        } catch (MailException e) {
            LOG.error("Failed to send message to {} with topic {} and content. Exception {} ", mail.getMailTo(), mail.getMailSubject(), mail.getMailContent(), e);
        }
    }

}

