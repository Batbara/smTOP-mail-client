package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;

public interface SmtpCommand {
    void sendToServer(String message) throws SendingCommandError;

    String receiveFromServer() throws ReceivingResponseError;

}
