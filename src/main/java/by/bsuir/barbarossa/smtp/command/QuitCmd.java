package by.bsuir.barbarossa.smtp.command;

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

public class QuitCmd implements SmtpCommand, ClientRequest {
    private static final String QUIT = "QUIT\r\n";
    private BufferedReader in;
    private PrintWriter out;

    public Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException {
        this.in = input;
        this.out = output;

        sendToServer(QUIT);
        String serverMessage = receiveFromServer();

        return new Response(serverMessage);
    }

    public void sendToServer(String message) throws SendingCommandError {
        out.write(message);
    }

    public String receiveFromServer() throws ReceivingResponseError {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new ReceivingResponseError("Error while reading server response from QUIT command", e);
        }
    }
}
