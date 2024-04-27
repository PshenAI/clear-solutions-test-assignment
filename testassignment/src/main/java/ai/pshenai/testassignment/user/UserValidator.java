package ai.pshenai.testassignment.user;

import ai.pshenai.testassignment.user.web.UserDTO;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class UserValidator {

    @Value("${user.min-age}")
    private static int minAge;
    private final static EmailValidator emailValidator = EmailValidator.getInstance();

    public static String validateUser(UserDTO userDTO) {
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

    public static boolean validateEmail(String email) {
        return emailValidator.isValid(email);
    }

    public static boolean validateFromAndToBirthDates(LocalDate from, LocalDate to) {
        return from.isAfter(to);
    }

    public static boolean validateUsersAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        LocalDate minAgeAgo = today.minusYears(minAge);

        return dateOfBirth.isBefore(minAgeAgo) || dateOfBirth.equals(minAgeAgo);
    }
}
