package by.bsuir.barbarossa.service.validation;

public class ServerValidator implements TypeValidator {
    public boolean checkServerHost(String host) throws ValidationException {
        checkEmpty(host);
        return true;
    }

    public boolean checkHostPort(String port) throws ValidationException {
        checkEmpty(port);
        isIntNumber(port);
        return true;
    }
}
