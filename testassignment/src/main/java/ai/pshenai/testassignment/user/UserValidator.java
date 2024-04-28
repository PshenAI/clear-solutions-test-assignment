package ai.pshenai.testassignment.user;

import ai.pshenai.testassignment.user.web.UserDTO;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserValidator {

    @Value("${user.min-age}")
    private int minAge;

    private final static String EMPTY_REQUEST_OBJECT_ERROR_MESSAGE = "Request body cannot be null";
    private final static String INVALID_EMAIL_PARAMETER_MESSAGE = "Invalid email";
    private final static String ILLEGAL_DATE_OF_BIRTH_MESSAGE = "Illegal date of birth";

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    public String validateUser(UserDTO userDTO) {
        String result = null;
        if(userDTO == null) {
            result = EMPTY_REQUEST_OBJECT_ERROR_MESSAGE;
        } else if(!validateEmail(userDTO.getEmail())) {
            result = INVALID_EMAIL_PARAMETER_MESSAGE;
        } else if (!validateUsersAge(userDTO.getDateOfBirth())) {
            result = ILLEGAL_DATE_OF_BIRTH_MESSAGE;
        }

        return result;
    }

    public boolean validateEmail(String email) {
        return emailValidator.isValid(email);
    }

    public boolean validateFromAndToBirthDates(LocalDate from, LocalDate to) {
        return from.isAfter(to);
    }

    public boolean validateUsersAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        LocalDate minAgeAgo = today.minusYears(minAge);

        return dateOfBirth.isBefore(minAgeAgo) || dateOfBirth.equals(minAgeAgo);
    }
}
