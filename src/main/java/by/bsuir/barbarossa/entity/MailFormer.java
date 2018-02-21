package by.bsuir.barbarossa.entity;

import by.bsuir.barbarossa.entity.Address;
import by.bsuir.barbarossa.entity.Content;
import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.User;

import java.net.InetAddress;

public class MailFormer {
    public Mail formMail(Envelope envelope, Content content) {
        Mail mail = new Mail();
        mail.setEnvelope(envelope);
        mail.setContent(content);
        return mail;
    }

    public Envelope formEnvelope(User sender, Address receiver) {
        Envelope envelope = new Envelope();
        envelope.setSender(sender);
        envelope.setRecipientAddresses(receiver);
        return envelope;
    }

    public User formSender(String userName, String password, Address eMail, InetAddress hostAddress) {
        User sender = new User();
        sender.seteMail(eMail);
        sender.setUserName(userName);
        sender.setPassword(password);
        sender.setLocalHostName(hostAddress);
        return sender;
    }

    public Content formContent(String subject, String body) {
        Content content = new Content();
        content.setSubject(subject);
        content.setBody(body);
        return content;
    }
}
