package by.bsuir.barbarossa.smtp;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.MainShell;
import by.bsuir.barbarossa.smtp.command.AuthCmd;
import by.bsuir.barbarossa.smtp.command.DataCmd;
import by.bsuir.barbarossa.smtp.command.EhloCmd;
import by.bsuir.barbarossa.smtp.command.MailCmd;
import by.bsuir.barbarossa.smtp.command.NoopCmd;
import by.bsuir.barbarossa.smtp.command.QuitCmd;
import by.bsuir.barbarossa.smtp.command.RcptCmd;
import by.bsuir.barbarossa.smtp.command.SmtpException;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Connector implements Runnable {
    private Logger LOGGER = Logger.getLogger(Connector.class);

    private MainShell mainShell;
    private BufferedReader in;
    private PrintWriter out;

    private SSLSocket socket;

    private Mail mail;
    private User user;
    private Server server;

    public Connector() {
    }

    public void setMainShell(MainShell mainShell) {
        this.mainShell = mainShell;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void run() {

        try {
            clearLog();
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
                    .getDefault();
            socket = (SSLSocket) sslsocketfactory.createSocket(server.getHost(), server.getPort());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Response greeting = new Response("**Created socket**\n", in.readLine());
            updateLog(greeting);
            ClientRequest[] commandList = {
                    new EhloCmd(in, out, mail.getEnvelope().getSender()),
                    new AuthCmd(in, out, mail.getEnvelope()),
                    new NoopCmd(in, out),
                    new MailCmd(in, out, mail.getEnvelope()),
                    new RcptCmd(in, out, mail.getEnvelope()),
                    new DataCmd(in, out, mail.getContent()),
                    new QuitCmd(in, out)
            };
            for (ClientRequest command : commandList) {

                Response response = execute(command);
                updateLog(response);
            }

        } catch (IOException e) {
            String message = "Connection error to server '%s', port '%d'";
            String formattedMessage = String.format(message, server.getHost(), server.getPort());
            LOGGER.error(formattedMessage, e);
            updateLog(new Response(formattedMessage));
        } catch (SmtpException e) {
            String message = "Error while executing smtp command";
            LOGGER.error(message, e);
            updateLog(new Response(message));
        } finally {
            if (out != null) {
                out.close();
            }
            try {
                if (in != null) {
                    in.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error while closing resources", e);
            }

        }
    }

    private void updateLog(Response message) {
        LOGGER.debug(message.getMessage());
        Display display = mainShell.getDisplay();
        Text logMemo = mainShell.getLogMemo();
        display.asyncExec(new Runnable() {
            @Override
            public void run() {
                if (!logMemo.isDisposed()) {
                    logMemo.append(message.getMessage());
                    logMemo.append("\n");
                }
            }
        });
    }
    private void clearLog(){
        Display display = mainShell.getDisplay();
        Text logMemo = mainShell.getLogMemo();
        display.asyncExec(new Runnable() {
            @Override
            public void run() {
                if (!logMemo.isDisposed()) {
                    logMemo.setText("");
                }
            }
        });
    }

    private Response execute(ClientRequest request) throws SmtpException {

        return request.execute();
    }
}
