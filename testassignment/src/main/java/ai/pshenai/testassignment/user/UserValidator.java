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

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    public String validateUser(UserDTO userDTO) {
        String result = null;
        if(userDTO == null) {
            result =  "Request body cannot be null";
        } else if(!validateEmail(userDTO.getEmail())) {
            result =  "Invalid email";
        } else if (!validateUsersAge(userDTO.getDateOfBirth())) {
            result =  "Invalid date of birth";
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
