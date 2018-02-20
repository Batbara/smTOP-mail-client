package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;
import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class NoopCmd implements SmtpCommand, ClientRequest {
    private static final String NOOP = "NOOP\r\n";

    private BufferedReader in;
    private PrintWriter out;
    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        this.in = input;
        this.out = output;

        sendToServer(NOOP);
        return new Response(receiveFromServer());
    }

    public void sendToServer(String message) throws SendingCommandError {
        out.write(message);
    }

    public String receiveFromServer() throws ReceivingResponseError {
        try {
            String line = in.readLine();
            return line;
        } catch (IOException e) {
            throw new ReceivingResponseError("Error while reading server response form NOOP command", e);
        }
    }
}
