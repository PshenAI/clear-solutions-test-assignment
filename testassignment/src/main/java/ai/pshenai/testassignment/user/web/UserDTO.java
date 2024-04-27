package ai.pshenai.testassignment.user.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date dateOfBirth;
    private UserAddressDTO address;
    private String phoneNumber;

    public UserDTO(String email, UserAddressDTO address, Date dateOfBirth, String lastName, String firstName, String phoneNumber) {
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }
}
