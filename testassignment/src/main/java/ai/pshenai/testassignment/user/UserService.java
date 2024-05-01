package ai.pshenai.testassignment.user;

import ai.pshenai.testassignment.user.entities.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    public UserDTO createUser(UserDTO userDTO) {
        // mock method
        return userDTO;
    }

    public UserDTO updateUser(UserDTO userDTO) {
        // mock method
        return userDTO;
    }

    public void deleteUser(UUID userId) {
        // mock method
    }

    public List<UserDTO> searchUsersDateOfBirth(LocalDate from, LocalDate to) {
        // mock method
        return List.of(UserDTO.getMockUserDTO(), UserDTO.getMockUserDTO(), UserDTO.getMockUserDTO());
    }
}
