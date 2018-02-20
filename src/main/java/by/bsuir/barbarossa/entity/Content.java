package by.bsuir.barbarossa.entity;

import java.io.Serializable;
import java.util.List;

public class Content implements Serializable {
    private String subject;
    private String body;
    public Content(){}

    public Content (String body){
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

    public void setBody(String body) {
        this.body = body;
    }
}
