package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Address;
import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.ClientRequest;
import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RcptCmd implements SmtpCommand, ClientRequest {
    private static final String RCPT = "RCPT TO:<%s>\r\n";

    private BufferedReader in;
    private PrintWriter out;
    private Address address;
    
    public void sendToServer(String message) throws SendingCommandError {

        out.write(message);
    }

    public String receiveFromServer() throws ReceivingResponseError {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new ReceivingResponseError("Error while reading server response form RCPT command", e);
        }
    }

    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        this.in = input;
        this.out = output;

        this.address = mail.getEnvelope().getRecipientAddresses();
        String receiverAddress = address.getMailAddress();
        String rcptCommand = String.format(RCPT, receiverAddress);

        sendToServer(rcptCommand);
        String serverMessage = receiveFromServer();
        return new Response(serverMessage);
    }
}
