package ai.pshenai.testassignment;

import ai.pshenai.testassignment.user.UserService;
import ai.pshenai.testassignment.user.UserValidator;
import ai.pshenai.testassignment.user.web.UserController;
import ai.pshenai.testassignment.user.web.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserValidator userValidator;

    private final static String EMPTY_REQUEST_OBJECT_ERROR_MESSAGE = "Request body cannot be null";
    private final static String INVALID_EMAIL_PARAMETER_MESSAGE = "Invalid email";
    private final static String ILLEGAL_DATE_OF_BIRTH_MESSAGE = "Invalid date of birth";
    private final static String ILLEGAL_FROM_AND_TO_PARAMETERS_MESSAGE = "From date must be before To date";

    @Test
    public void testCreateUserSuccess() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(null);
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mockfirstname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mockmail@clearsolutions.com"));

        verify(userService).createUser(userDTO);
    }

    @Test
    public void testEmptyRequestObjectOnUserCreation() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(EMPTY_REQUEST_OBJECT_ERROR_MESSAGE);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(EMPTY_REQUEST_OBJECT_ERROR_MESSAGE));
    }

    @Test
    public void testAgeNotValidatedOnUserCreation() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(ILLEGAL_DATE_OF_BIRTH_MESSAGE);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(ILLEGAL_DATE_OF_BIRTH_MESSAGE));
    }

    @Test
    public void testEmailNotValidatedOnUserCreation() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(INVALID_EMAIL_PARAMETER_MESSAGE);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(INVALID_EMAIL_PARAMETER_MESSAGE));
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(null);
        when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mockfirstname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mockmail@clearsolutions.com"));

        verify(userService).updateUser(userDTO);
    }

    @Test
    public void testAgeNotValidatedOnUserUpdate() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.getMockUserDTO();

        when(userValidator.validateUser(any(UserDTO.class))).thenReturn(ILLEGAL_DATE_OF_BIRTH_MESSAGE);
        when(userService.updateUser(any(UserDTO.class))).thenReturn(userDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(ILLEGAL_DATE_OF_BIRTH_MESSAGE));
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();

        doNothing().when(userService).deleteUser(userId);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userId)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSearchUsersDateOfBirthSuccess() throws Exception {
        // Given
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2020, 1, 1);

        List<UserDTO> users = new ArrayList<>();
        users.add(UserDTO.getMockUserDTO());
        users.add(UserDTO.getMockUserDTO());
        users.add(UserDTO.getMockUserDTO());

        when(userValidator.validateFromAndToBirthDates(fromDate, toDate)).thenReturn(false);
        when(userService.searchUsersDateOfBirth(fromDate, toDate)).thenReturn(users);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/search")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService).searchUsersDateOfBirth(argThat(a -> a.equals(fromDate)), argThat(a -> a.equals(toDate)));
    }

    @Test
    public void testSearchUsersDateOfBirthValidationError() throws Exception {
        // Given
        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        when(userValidator.validateFromAndToBirthDates(fromDate, toDate)).thenReturn(true);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/search")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(ILLEGAL_FROM_AND_TO_PARAMETERS_MESSAGE));
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(obj);
    }
}
