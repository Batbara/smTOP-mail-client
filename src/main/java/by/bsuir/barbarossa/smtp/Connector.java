package by.bsuir.barbarossa.smtp;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.MainShell;
import by.bsuir.barbarossa.smtp.command.AuthCmd;
import by.bsuir.barbarossa.smtp.command.DataCmd;
import by.bsuir.barbarossa.smtp.command.EhloCmd;
import by.bsuir.barbarossa.smtp.command.HeloCmd;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
          //  socket.startHandshake();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Response greeting = new Response("**Created socket**\n", in.readLine());
            updateLog(greeting);
            ClientRequest[] commandList = {
                  //  new HeloCmd(in, out, mail.getEnvelope().getSender()),
                    new EhloCmd(in, out, mail.getEnvelope().getSender()),
                    // new NoopCmd(),
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
            //TODO: log it
        } catch (SmtpException e) {
            //TODO: log this too
        } finally {
            if(out!=null) {
                out.close();
            }
            try {
                if(in!=null) {
                    in.close();
                }
                socket.close();
            } catch (IOException e) {
               //log it
            }

        }
    }
    private void closeResources() throws SmtpException {
        out.close();
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new SmtpException("Closing resources exception", e);
        }

    }
    private void updateLog(Response message) {
        logger.debug(message.getMessage());
        Display display = mainShell.getDisplay();
        Text logMemo = mainShell.getLogMemo();
        display.asyncExec(new Runnable() {
            @Override
            public void run() {
                if (!logMemo.isDisposed()) {
                    logMemo.append(message.getMessage());
                    logMemo.getParent().layout();
                }
            }
        });
      //  System.err.println(message.getMessage());
    }

    private Response execute(ClientRequest request) throws SmtpException {

        return request.execute();
    }
}
