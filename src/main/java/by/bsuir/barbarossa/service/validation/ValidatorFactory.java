package by.bsuir.barbarossa.service.validation;

public class ValidatorFactory {
    private static ValidatorFactory instance = new ValidatorFactory();
    private ServerValidator serverValidator = new ServerValidator();
    private MailValidator mailValidator =  new MailValidator();
    private ValidatorFactory(){}

    public static ValidatorFactory getInstance() {
        return instance;
    }

    public ServerValidator getServerValidator() {
        return serverValidator;
    }

    public MailValidator getMailValidator() {
        return mailValidator;
    }
}
