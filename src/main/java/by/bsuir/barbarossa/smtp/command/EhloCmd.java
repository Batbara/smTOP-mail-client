package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EhloCmd implements SmtpCommand, ClientRequest {
    private static final String EHLO = "EHLO %s \r\n";

    private BufferedReader in;
    private PrintWriter out;
    private User sender;

    public EhloCmd(BufferedReader in, PrintWriter out, User sender) {
        this.in = in;
        this.out = out;
        this.sender = sender;
    }

    @Override
    public Response execute() throws SmtpException {
        String localHostAddress = sender.getLocalHostAddress();
        String ehloCommand = String.format(EHLO, localHostAddress);

        sendToServer(ehloCommand);

        String serverMessage = receiveFromServer();
        return new Response(ehloCommand, serverMessage);
    }

    @Override
    public void sendToServer(String message) {
        out.write(message);
        out.flush();
    }

    @Override
    public String receiveFromServer() throws ReceivingResponseException {
        try {
            StringBuilder builder = new StringBuilder();

            do {
                builder.append(in.readLine()).append("\n");
            } while (in.ready());
            return builder.toString();
        } catch (IOException e) {
            throw new ReceivingResponseException("Error while reading server response from EHLO command", e);
        }
    }
}
