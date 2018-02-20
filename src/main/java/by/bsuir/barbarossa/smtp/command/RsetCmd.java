package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;
import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class RsetCmd implements SmtpCommand, ClientRequest {
    @Override
    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        return null;
    }

    @Override
    public void sendToServer(String message) throws SendingCommandError {

    }

    @Override
    public String receiveFromServer() throws ReceivingResponseError {
        return null;
    }
}
