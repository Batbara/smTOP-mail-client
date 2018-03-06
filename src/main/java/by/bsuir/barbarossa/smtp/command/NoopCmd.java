package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class NoopCmd implements SmtpCommand, ClientRequest {
    private static final String NOOP = "NOOP\r\n";

    private BufferedReader in;
    private PrintWriter out;

    public NoopCmd(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public Response execute() throws SmtpException {

        sendToServer(NOOP);
        return new Response(NOOP, receiveFromServer());
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
            throw new ReceivingResponseException("Error while reading server response form NOOP command", e);
        }
    }
}
