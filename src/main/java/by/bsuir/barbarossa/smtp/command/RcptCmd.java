package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RcptCmd implements SmtpCommand, ClientRequest {
    private static final String RCPT = "RCPT TO:<%s>\r\n";

    private BufferedReader in;
    private PrintWriter out;
    private Envelope envelope;

    public RcptCmd(BufferedReader in, PrintWriter out, Envelope envelope) {
        this.in = in;
        this.out = out;
        this.envelope = envelope;
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
            throw new ReceivingResponseException("Error while reading server response form RCPT command", e);
        }
    }

    public Response execute() throws SmtpException {

        String receiverAddress = envelope.getRecipientAddresses().getMailAddress();
        String rcptCommand = String.format(RCPT, receiverAddress);

        sendToServer(rcptCommand);
        String serverMessage = receiveFromServer();
        return new Response(rcptCommand, serverMessage);
    }
}
