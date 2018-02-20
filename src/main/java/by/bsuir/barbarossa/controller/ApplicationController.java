package by.bsuir.barbarossa.controller;

import by.bsuir.barbarossa.entity.Address;
import by.bsuir.barbarossa.entity.Content;
import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.MainShell;
import by.bsuir.barbarossa.smtp.Connector;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationController {
    private MainShell mainShell;
    public ApplicationController(){
        mainShell = new MainShell();
        Connector connector = new Connector();
        connector.setMainShell(mainShell);

        Mail mail = null;
        try {
            mail = formMail();
        } catch (SmtpException e) {
            e.printStackTrace();
        }
        Server server = new Server("smtp.gmail.com",465);

       // Server server = new Server("smtp.mail.ru",465);
        connector.setMail(mail);
        connector.setServer(server);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(connector);
    }
    public void startApplication(){
        mainShell.start();
    }
    private Mail formMail() throws SmtpException {
        Mail mail = new Mail();

        Content content = new Content();
        content.setBody("Hello there!");

        Envelope envelope = new Envelope();
        try {
            InetAddress localHostAddress = InetAddress.getLocalHost();

        User user = new User("alien.katte","kNICKELback2012", localHostAddress);
        user.seteMail(new Address("alien.katte@gmail.com"));

        Address recevier  = new Address("talahbarbara@gmail.com");
        envelope.setSender(user);
        envelope.setRecipientAddresses(recevier);

        mail.setContent(content);
        mail.setEnvelope(envelope);
        return mail;
        } catch (UnknownHostException e) {
           throw new SmtpException("Unknown host", e);
        }
    }
}
