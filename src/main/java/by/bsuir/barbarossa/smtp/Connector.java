package by.bsuir.barbarossa.smtp;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.MainShell;
import by.bsuir.barbarossa.smtp.command.AuthCmd;
import by.bsuir.barbarossa.smtp.command.DataCmd;
import by.bsuir.barbarossa.smtp.command.HeloCmd;
import by.bsuir.barbarossa.smtp.command.MailCmd;
import by.bsuir.barbarossa.smtp.command.NoopCmd;
import by.bsuir.barbarossa.smtp.command.QuitCmd;
import by.bsuir.barbarossa.smtp.command.RcptCmd;
import by.bsuir.barbarossa.smtp.exception.SmtpException;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Connector implements Runnable {
    private Logger logger = Logger.getLogger(Connector.class);

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

    public boolean send(String content, String from, String to)
            throws IOException {
        Socket smtpPipe;
        InputStream inn;
        OutputStream outt;
        BufferedReader msg;
        smtpPipe = new Socket(server.getHost(), server.getPort());

        inn = smtpPipe.getInputStream();
        outt = smtpPipe.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inn));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(outt), true);

        String initialID = in.readLine();
        System.out.println(initialID);
       // System.out.println("HELO " + localhost.getHostName());
      //  out.println("HELO " + localhost.getHostName());
        String welcome = in.readLine();
        System.out.println(welcome);
        System.out.println("MAIL From:<" + from + ">");
        out.println("MAIL From:<" + from + ">");
        String senderOK = in.readLine();
        System.out.println(senderOK);
        System.out.println("RCPT TO:<" + to + ">");
        out.println("RCPT TO:<" + to + ">");
        String recipientOK = in.readLine();
        System.out.println(recipientOK);
        System.out.println("DATA");
        out.println("DATA");
        out.println(content);
       /* String line;
        while ((line = msg.readLine()) != null) {
            out.println(line);
        }*/
        System.out.println(".");
        out.println(".");
        String acceptedOK = in.readLine();
        System.out.println(acceptedOK);
        System.out.println("QUIT");
        out.println("QUIT");
        return true;
    }

    public void run() {

        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
                    .getDefault();
            socket = (SSLSocket) sslsocketfactory.createSocket(server.getHost(), server.getPort());
            socket.startHandshake();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            ClientRequest[] commandList = {
                    new HeloCmd(),
                   // new NoopCmd(),
                    new AuthCmd(),
                    new NoopCmd(),
                    new MailCmd(),
                    new RcptCmd(),
                    new DataCmd(),
                    new QuitCmd()
            };
            for (ClientRequest command : commandList) {

                Response response = execute(command);
                updateLog(response);
            }

        } catch (IOException e) {
            //TODO: log it
        } catch (SmtpException e) {
            //TODO: log this too
        }
    }

    private void updateLog(Response message) {
        //logger.info(message.getResponse());
        System.err.println(message.getResponse());
    }

    private Response execute(ClientRequest request) throws SmtpException {

        return request.execute(in, out, mail);
    }
}
