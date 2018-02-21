package by.bsuir.barbarossa.smtp.command;

public interface SmtpCommand {
    void sendToServer(String message) throws SendingCommandException;

    String receiveFromServer() throws ReceivingResponseException;

}
