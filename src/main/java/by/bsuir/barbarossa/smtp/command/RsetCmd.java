package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RsetCmd implements SmtpCommand, ClientRequest {
    private static final String RSET = "RSET\r\n";

    private BufferedReader in;
    private PrintWriter out;

    public RsetCmd(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public Response execute() throws SmtpException {

        sendToServer(RSET);
        String serverMessage = receiveFromServer();
        return new Response(RSET, serverMessage);
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
            throw new ReceivingResponseException("Error while reading server response from RSET command", e);
        }
    }
}
