package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Mail implements Serializable {
    private Envelope envelope;
    private Content content;
    public Mail(){}

    public Envelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
