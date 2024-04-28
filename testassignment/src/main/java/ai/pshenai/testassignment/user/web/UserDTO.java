package ai.pshenai.testassignment.user.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private LocalDate dateOfBirth;
    private UserAddressDTO address;
    private String phoneNumber;

    public UserDTO(String email, UserAddressDTO address, LocalDate dateOfBirth, String lastName, String firstName, String phoneNumber) {
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public static UserDTO getMockUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("mockmail@clearsolutions.com");
        userDTO.setFirstName("mockfirstname");
        userDTO.setLastName("mocklastname");
        userDTO.setDateOfBirth(LocalDate.of(2001, 5, 25));
        userDTO.setPhoneNumber("mockphone");
        userDTO.setAddress(UserAddressDTO.getMockUserAddressObject());
        return userDTO;
    }
}
