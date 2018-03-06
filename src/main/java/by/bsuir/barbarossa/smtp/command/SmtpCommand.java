package by.bsuir.barbarossa.smtp.command;

public interface SmtpCommand {
    void sendToServer(String message);

    String receiveFromServer() throws ReceivingResponseException;

}
