package by.bsuir.barbarossa.smtp;

import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.command.SmtpException;

public interface ClientRequest  {

    Response execute() throws SmtpException;
}
