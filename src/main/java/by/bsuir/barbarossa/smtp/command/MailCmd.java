package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;
import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MailCmd implements  SmtpCommand, ClientRequest {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    private BufferedReader in;
    private PrintWriter out;
    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        this.in = input;
        this.out = output;

        User sender = mail.getEnvelope().getSender();
        String mailAddress = sender.getMailAddress();
        String mailCommand = String.format(MAIL, mailAddress);
        sendToServer(mailCommand);

        String serverMessage = receiveFromServer();
        return new Response(serverMessage);
    }

    public void sendToServer(String message) throws SendingCommandError {

        out.write(message);
    }

    public String receiveFromServer() throws ReceivingResponseError {
        try {
            String line = in.readLine();
            return line;
        } catch (IOException e) {
            throw new ReceivingResponseError("Error while reading server response form MAIL command", e);
        }
    }
}
