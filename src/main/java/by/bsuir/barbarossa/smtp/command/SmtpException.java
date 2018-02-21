package by.bsuir.barbarossa.smtp.command;

public class SmtpException extends Exception {
    public SmtpException() {
        super();
    }

    public SmtpException(String message) {
        super(message);
    }

    public SmtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmtpException(Throwable cause) {
        super(cause);
    }

}
