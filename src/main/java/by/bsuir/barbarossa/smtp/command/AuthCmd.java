package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.smtp.ClientRequest;
import by.bsuir.barbarossa.smtp.exception.ReceivingResponseError;
import by.bsuir.barbarossa.smtp.exception.SendingCommandError;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AuthCmd implements SmtpCommand, ClientRequest {
    private static final String AUTH = "AUTH LOGIN \r\n";
    private BufferedReader in;
    private PrintWriter out;

    @Override
    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        this.in = input;
        this.out = output;

        sendToServer(AUTH);
        String serverResponse = receiveFromServer();
        if (serverResponse != null) {
            User sender = mail.getEnvelope().getSender();
            String userName = sender.getUserName();
            String password = sender.getPassword();

            sendToServer(userName);

            sendToServer(password);
            return new Response(receiveFromServer());
        } else {
            throw new SmtpException("error");
        }

    }

    @Override
    public void sendToServer(String message) throws SendingCommandError {
        out.write(message);
        out.flush();
    }

    @Override
    public String receiveFromServer() throws ReceivingResponseError {
        try {
            String response = null;
            while (in.readLine()!=null) {
                response = response+in.readLine();
           }
            return response;
        } catch (IOException e) {
            throw new ReceivingResponseError("Error while reading server response form AUTH command", e);
        }
    }
}
