package ai.pshenai.testassignment.user.web;

import ai.pshenai.testassignment.user.UserService;
import ai.pshenai.testassignment.user.UserValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            String errorMessage = UserValidator.validateUser(userDTO);

            UserDTO createdUser = userService.createUser(userDTO);

            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(errorMessage);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for users");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            String errorMessage = UserValidator.validateUser(userDTO);

            UserDTO createdUser = userService.updateUser(userDTO);

            if(errorMessage != null) {
                return ResponseEntity.badRequest().body(errorMessage);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for users");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(UUID userId) {
        try {
            userService.deleteUser(userId);

            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for users");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsersDateOfBirth(
                @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        try{
            if (UserValidator.validateFromAndToBirthDates(fromDate, toDate)) {
                return ResponseEntity.badRequest().body("From date must be before To date");
            }

            List<UserDTO> users = userService.searchUsersDateOfBirth(fromDate, toDate);

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for users");
        }
    }
}
