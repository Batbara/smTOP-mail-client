package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.smtp.command.SmtpException;

public class SendingCommandException extends SmtpException {
    public SendingCommandException() {
        super();
    }

    public SendingCommandException(String message) {
        super(message);
    }

    public SendingCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendingCommandException(Throwable cause) {
        super(cause);
    }
}
