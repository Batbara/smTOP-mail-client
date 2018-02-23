package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Content;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DataCmd implements SmtpCommand, ClientRequest {
    private static final String DATA = "DATA\r\n";

    private BufferedReader in;
    private PrintWriter out;
    private Content content;

    public DataCmd(BufferedReader in, PrintWriter out, Content content) {
        this.in = in;
        this.out = out;
        this.content = content;
    }

    public Response execute() throws SmtpException {

        sendToServer(DATA);
        String serverMessage = receiveFromServer();
        if (!serverMessage.startsWith("5")) {
            sendToServer(content.getMailContent());
            sendToServer("\r\n.\r\n");
            serverMessage = receiveFromServer();
        }

        return new Response(DATA, serverMessage);
    }

    public void sendToServer(String message) throws SendingCommandException {
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
            throw new ReceivingResponseException("Error while reading server response from DATA command", e);
        }
    }
}
