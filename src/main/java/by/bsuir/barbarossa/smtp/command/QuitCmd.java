package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class QuitCmd implements SmtpCommand, ClientRequest {
    private static final String QUIT = "QUIT\r\n";
    private BufferedReader in;
    private PrintWriter out;

    public QuitCmd(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public Response execute() throws SmtpException {

        sendToServer(QUIT);
        String serverMessage = receiveFromServer();

        return new Response(QUIT, serverMessage);
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
            throw new ReceivingResponseException("Error while reading server response from QUIT command", e);
        }
    }
}
