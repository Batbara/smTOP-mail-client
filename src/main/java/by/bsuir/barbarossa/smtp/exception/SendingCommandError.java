package by.bsuir.barbarossa.smtp.exception;

public class SendingCommandError extends SmtpException {
    public SendingCommandError() {
        super();
    }

    public SendingCommandError(String message) {
        super(message);
    }

    public SendingCommandError(String message, Throwable cause) {
        super(message, cause);
    }

    public SendingCommandError(Throwable cause) {
        super(cause);
    }
}
