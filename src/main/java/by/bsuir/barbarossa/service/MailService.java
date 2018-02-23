package by.bsuir.barbarossa.service;

import by.bsuir.barbarossa.entity.Address;
import by.bsuir.barbarossa.entity.Content;
import by.bsuir.barbarossa.entity.EntityFormerFactory;
import by.bsuir.barbarossa.entity.Envelope;
import by.bsuir.barbarossa.entity.Mail;
import by.bsuir.barbarossa.entity.MailFormer;
import by.bsuir.barbarossa.entity.User;
import by.bsuir.barbarossa.gui.InputFieldMap;
import by.bsuir.barbarossa.gui.NoSuchTextFieldException;
import by.bsuir.barbarossa.gui.LabelText;
import by.bsuir.barbarossa.service.validation.MailValidator;
import by.bsuir.barbarossa.service.validation.ValidationException;
import by.bsuir.barbarossa.service.validation.ValidatorFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MailService {
    public Mail formMail(InputFieldMap inputFieldMap) throws ServiceException {
        MailValidator validator = ValidatorFactory.getInstance().getMailValidator();
        MailFormer mailFormer = EntityFormerFactory.getInstance().getMailFormer();

         //String userName = textFieldMap.getTextOf(TextConstant.U)
        //  User sender = mailFormer.formSender()
        try {

            String mailSubject = inputFieldMap.getTextOf(LabelText.LETTER_SUBJECT);
            String mailBody = inputFieldMap.getTextOf(LabelText.LETTER_CONTENT);
            validator.checkMailContent(mailSubject, mailBody);

            Content mailContent = mailFormer.formContent(mailSubject, mailBody);

            String senderAddress = inputFieldMap.getTextOf(LabelText.FROM_LABEL);
            validator.checkMailAddress(senderAddress);

            String userName = inputFieldMap.getTextOf(LabelText.USER_NAME);
            String password = inputFieldMap.getTextOf(LabelText.PASSWORD);
            //TODO: validate user name and password

            User user = mailFormer.formSender(userName, password, new Address(senderAddress), InetAddress.getLocalHost());

            String receiverAddress = inputFieldMap.getTextOf(LabelText.TO_LABEL);
            validator.checkMailAddress(receiverAddress);


            Envelope envelope = mailFormer.formEnvelope(user, new Address(receiverAddress));
            Mail mail = mailFormer.formMail(envelope, mailContent);
            return mail;
        } catch (NoSuchTextFieldException e) {
            throw new ServiceException("Field not found", e);
        } catch (ValidationException e) {
            throw new ServiceException("Invalid parameters", e);
        } catch (UnknownHostException e) {
            throw new ServiceException("Unknown local host", e);
        }
    }

}
