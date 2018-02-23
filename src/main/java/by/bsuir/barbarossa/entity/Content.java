package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Content implements Serializable {
    private final static String SUBJECT_CAPTION = "Subject: ";
    private String subject;
    private String body;

    public Content() {
    }

    public Content(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public String getMailContent() {
        return SUBJECT_CAPTION + subject + "\n" + body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
