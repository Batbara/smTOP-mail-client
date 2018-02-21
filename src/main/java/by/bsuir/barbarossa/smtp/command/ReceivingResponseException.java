package by.bsuir.barbarossa.smtp.command;

import by.bsuir.barbarossa.smtp.command.SmtpException;

public class ReceivingResponseException extends SmtpException {
    public ReceivingResponseException() {
        super();
    }

    public ReceivingResponseException(String message) {
        super(message);
    }

    public ReceivingResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceivingResponseException(Throwable cause) {
        super(cause);
    }
}
