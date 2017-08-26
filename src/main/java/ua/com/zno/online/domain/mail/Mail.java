package ua.com.zno.online.domain.mail;

import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by g.didorenko on 26.08.17.
 */

public class Mail {

    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;

    private List<Object> attachments;
    private Map<String, Object> model = new HashMap<>(7, 1);

    public Mail(String mailTo, String mailSubject, String mailContent) {
        this(mailTo, mailSubject, mailContent, null);
    }

    public Mail(String mailTo, String mailSubject, String mailContent, List<Object> attachments) {
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.mailContent = mailContent;
        this.attachments = attachments;

        model.put("text", this.mailContent);
        model.put("signature", "ZNO.NET.UA Team");
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getMailCc() {
        return mailCc;
    }

    public String getMailBcc() {
        return mailBcc;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
