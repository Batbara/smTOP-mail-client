package by.bsuir.barbarossa.service;

import by.bsuir.barbarossa.entity.EntityFormerFactory;
import by.bsuir.barbarossa.entity.ServerFormer;
import by.bsuir.barbarossa.entity.Server;
import by.bsuir.barbarossa.gui.NoSuchTextFieldException;
import by.bsuir.barbarossa.gui.LabelText;
import by.bsuir.barbarossa.gui.InputFieldMap;
import by.bsuir.barbarossa.service.validation.ServerValidator;
import by.bsuir.barbarossa.service.validation.ValidationException;
import by.bsuir.barbarossa.service.validation.ValidatorFactory;

public class ServerService {
    public Server formServerInfo(InputFieldMap fieldMap) throws ServiceException {
        try {
            String serverHost = fieldMap.getTextOf(LabelText.SERVER);
            String serverPort = fieldMap.getTextOf(LabelText.PORT_NUMBER);
            ServerValidator validator = ValidatorFactory.getInstance().getServerValidator();
            validator.checkHostPort(serverHost);
            validator.checkHostPort(serverPort);

            ServerFormer serverFormer = EntityFormerFactory.getInstance().getServerFormer();
            return serverFormer.formServerInfo(serverHost, serverPort);
        } catch (NoSuchTextFieldException e) {
            throw new ServiceException("Field not found", e);
        } catch (ValidationException e) {
            throw new ServiceException("Invalid server parameters", e);
        }
    }
}
