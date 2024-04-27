package ai.pshenai.testassignment.user.web;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/users")
public class UserController {

    @Value("${user.min-age}")
    private int minAge;
    private final EmailValidator emailValidator;

    public UserController(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }
}
