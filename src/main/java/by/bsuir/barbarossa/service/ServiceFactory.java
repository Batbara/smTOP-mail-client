package by.bsuir.barbarossa.service;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();
    private ServerService serverService = new ServerService();
    private MailService mailService = new MailService();

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ServerService getServerService() {
        return serverService;
    }

    public MailService getMailService() {
        return mailService;
    }
}
