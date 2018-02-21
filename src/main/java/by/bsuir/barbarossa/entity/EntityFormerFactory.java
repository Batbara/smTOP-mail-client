package by.bsuir.barbarossa.entity;

public class EntityFormerFactory {
    private static EntityFormerFactory instance = new EntityFormerFactory();
    private ServerFormer serverFormer = new ServerFormer();
    private MailFormer mailFormer = new MailFormer();

    private EntityFormerFactory(){}

    public static EntityFormerFactory getInstance() {
        return instance;
    }

    public ServerFormer getServerFormer() {
        return serverFormer;
    }

    public MailFormer getMailFormer() {
        return mailFormer;
    }
}
