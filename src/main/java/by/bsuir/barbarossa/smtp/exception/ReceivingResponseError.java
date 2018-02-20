package by.bsuir.barbarossa.smtp.exception;

public class ReceivingResponseError extends SmtpException{
    public ReceivingResponseError() {
        super();
    }

    public ReceivingResponseError(String message) {
        super(message);
    }

    public ReceivingResponseError(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceivingResponseError(Throwable cause) {
        super(cause);
    }
}
