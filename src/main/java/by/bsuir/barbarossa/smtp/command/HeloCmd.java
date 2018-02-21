package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HeloCmd implements SmtpCommand, ClientRequest {
    private static final String HELO = "HELO %s \r\n";

    private BufferedReader in;
    private PrintWriter out;
    private User sender;

    public HeloCmd(BufferedReader in, PrintWriter out, User sender) {
        this.in = in;
        this.out = out;
        this.sender = sender;
    }

    public Response execute() throws SmtpException {

        String localHostAddress = sender.getLocalHostAddress();
        String heloCommand = String.format(HELO, localHostAddress);

        sendToServer(heloCommand);
        String serverMessage = receiveFromServer();
        return new Response(heloCommand, serverMessage);
    }

    public void sendToServer(String message) {
        out.write(message);
        out.flush();
    }

    public String receiveFromServer() throws ReceivingResponseException {
        try {
           return in.readLine();
        } catch (IOException e) {
            throw new ReceivingResponseException("Error while reading server response from HELO command", e);
        }
    }
}
