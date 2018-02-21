package by.bsuir.barbarossa.controller;

import by.bsuir.barbarossa.entity.Address;
import by.bsuir.barbarossa.entity.Content;
import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.MainShell;
import by.bsuir.barbarossa.gui.InputFieldMap;
import by.bsuir.barbarossa.service.MailService;
import by.bsuir.barbarossa.service.ServerService;
import by.bsuir.barbarossa.service.ServiceException;
import by.bsuir.barbarossa.service.ServiceFactory;
import by.bsuir.barbarossa.smtp.Connector;
import by.bsuir.barbarossa.smtp.command.SmtpException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationController implements Observer {
    private MainShell mainShell;

    public ApplicationController() {
        mainShell = new MainShell();
        mainShell.addControllerAsObserver(this);
        Connector connector = new Connector();
        connector.setMainShell(mainShell);

        Mail mail = null;
        try {
            mail = formMail();
        } catch (SmtpException e) {
            e.printStackTrace();
        }
        //Server server = new Server("smtp.gmail.com", 465);

        Server server = new Server("smtp.mail.ru",465);
        connector.setMail(mail);
        connector.setServer(server);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(connector);
    }

    public void startApplication() {
        mainShell.start();
    }

    private Mail formMail() throws SmtpException {
        Mail mail = new Mail();

        Content content = new Content();
        content.setBody("Hello there!");

        Envelope envelope = new Envelope();
        try {
            InetAddress localHostAddress = InetAddress.getLocalHost();

            User user = new User("talah-barbara@mail.ru", "barbara12", localHostAddress);
            user.seteMail(new Address("talah-barbara@mail.ru"));

            Address recevier = new Address("talahbarbara@gmail.com");
            envelope.setSender(user);
            envelope.setRecipientAddresses(recevier);

            mail.setContent(content);
            mail.setEnvelope(envelope);
            return mail;
        } catch (UnknownHostException e) {
            throw new SmtpException("Unknown host", e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof InputFieldMap) {
            InputFieldMap inputFieldMap = (InputFieldMap) arg;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            try {
                ServerService serverService = serviceFactory.getServerService();
                Server server = serverService.formServerInfo(inputFieldMap);

                MailService mailService = serviceFactory.getMailService();
                Mail mail = mailService.formMail(inputFieldMap);

                Connector connector = new Connector();
                connector.setMainShell(mainShell);
                connector.setMail(mail);
                connector.setServer(server);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(connector);
            } catch (ServiceException e) {
                //  display error
                System.err.println("ERROR!!");
            }
        }
    }
}
