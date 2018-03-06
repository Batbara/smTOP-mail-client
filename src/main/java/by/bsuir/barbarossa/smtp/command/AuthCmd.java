package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthCmd implements SmtpCommand, ClientRequest {
    private static final String AUTH = "AUTH PLAIN %s\r\n";
    private BufferedReader in;
    private PrintWriter out;
    private Envelope envelope;

    public AuthCmd(BufferedReader in, PrintWriter out, Envelope envelope) {
        this.in = in;
        this.out = out;
        this.envelope = envelope;
    }

    @Override
    public Response execute() throws SmtpException {
        User sender = envelope.getSender();

        String credentials = sender.getEncodedCredentials();

        String clientRequest = String.format(AUTH, credentials);
        sendToServer(clientRequest);
        String serverResponse = receiveFromServer();

        return new Response(clientRequest, serverResponse);


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
            throw new ReceivingResponseException("Error while reading server response form AUTH command", e);
        }
    }
}
