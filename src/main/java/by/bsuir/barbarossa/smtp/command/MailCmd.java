package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MailCmd implements  SmtpCommand, ClientRequest {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    private BufferedReader in;
    private PrintWriter out;
    private Envelope envelope;

    public MailCmd(BufferedReader in, PrintWriter out, Envelope envelope) {
        this.in = in;
        this.out = out;
        this.envelope = envelope;
    }

    public Response execute() throws SmtpException {

        User sender = envelope.getSender();
        String mailAddress = sender.getMailAddress();
        String mailCommand = String.format(MAIL, mailAddress);
        sendToServer(mailCommand);

        String serverMessage = receiveFromServer();
        return new Response(mailCommand, serverMessage);
    }

    public void sendToServer(String message) {

        out.write(message);
        out.flush();
    }

    public String receiveFromServer() throws ReceivingResponseException {
        try {
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(in.readLine());
            } while (in.ready());
            return builder.toString();
        } catch (IOException e) {
            throw new ReceivingResponseException("Error while reading server response form MAIL command", e);
        }
    }
}
