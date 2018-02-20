package by.bsuir.barbarossa.smtp;

import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.Response;
import by.bsuir.barbarossa.smtp.exception.SmtpException;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ClientRequest  {

    Response execute(BufferedReader input, PrintWriter output, Mail mail) throws SmtpException;
}
