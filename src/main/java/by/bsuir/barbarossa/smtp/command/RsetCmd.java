package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

public class RsetCmd implements SmtpCommand, ClientRequest {
    @Override
    public Response execute() throws SmtpException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void sendToServer(String message) throws SendingCommandException {

    }

    @Override
    public String receiveFromServer() throws ReceivingResponseException {
        return null;
    }
}
