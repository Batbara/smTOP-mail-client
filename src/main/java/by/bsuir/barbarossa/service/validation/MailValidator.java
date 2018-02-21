package by.bsuir.barbarossa.service.validation;

import java.util.regex.Pattern;

public class MailValidator implements TypeValidator {
    private final static String EMAIL_REGEXP = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEXP);

    public boolean checkMailContent(String subject, String body) throws ValidationException {
        checkEmpty(subject);
        checkEmpty(body);
        return true;
    }

    public boolean checkMailAddress(String address) throws ValidationException {
        checkEmpty(address);
        if (emailPattern.matcher(address).matches()) {
            return true;
        }
        throw new ValidationException("Invalid e-mail address");
    }
}
