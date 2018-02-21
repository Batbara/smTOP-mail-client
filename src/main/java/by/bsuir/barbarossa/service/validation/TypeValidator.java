package by.bsuir.barbarossa.service.validation;

import java.util.regex.Pattern;

public interface TypeValidator {
    default boolean isIntNumber(String number) {
        Pattern numberPattern = Pattern.compile("\\d+");
        return numberPattern.matcher(number).matches();
    }
    default boolean checkEmpty(String parameter) throws ValidationException {
        if(parameter == null || parameter.isEmpty()){
            throw new ValidationException("Input parameter is empty");
        }
        return true;
    }
}
