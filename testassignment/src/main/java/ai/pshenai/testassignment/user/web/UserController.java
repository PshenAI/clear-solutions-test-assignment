package ai.pshenai.testassignment.user.web;

import ai.pshenai.testassignment.user.UserService;
import ai.pshenai.testassignment.user.UserValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserValidator userValidator;
    private final UserService userService;

    public UserController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        String errorMessage = userValidator.validateUser(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        if (errorMessage != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        String errorMessage = userValidator.validateUser(userDTO);

        UserDTO createdUser = userService.updateUser(userDTO);

        if(errorMessage != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(UUID userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsersDateOfBirth(
                @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        if (userValidator.validateFromAndToBirthDates(fromDate, toDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From date must be before To date");
        }

        List<UserDTO> users = userService.searchUsersDateOfBirth(fromDate, toDate);

        return ResponseEntity.ok(users);
    }
}
