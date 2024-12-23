package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);
    }

    @Test
    public void testCreateNewUser() {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createNewUser(userDTO);

        verify(userService, times(1)).createUser(any(UserDTO.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getId() == 1L;
    }

    @Test
    public void testCreateNewUser_Conflict() {
        when(userService.createUser(any(UserDTO.class))).thenThrow(new EmailAlreadyInUseException("User with same email already exists!"));

        ResponseEntity<UserDTO> response = userController.createNewUser(userDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); // Assert conflict response
        assertNull(response.getBody()); // No body content expected
    }

    @Test
    public void testModifyUser() {
        when(userService.modifyUser(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.modifyUser(1L, userDTO);

        verify(userService, times(1)).modifyUser(any(UserDTO.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getId() == 1L;
    }

    @Test
    public void testModifyUser_NotFound() {
        when(userService.modifyUser(any(UserDTO.class))).thenThrow(new EntityNotFoundException("User not found!"));

        ResponseEntity<UserDTO> response = userController.modifyUser(1L, userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Assert not found response
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userService).deleteUser(any(UserDTO.class));

        ResponseEntity<String> response = userController.deleteUser(userDTO);

        verify(userService, times(1)).deleteUser(any(UserDTO.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(response.getBody(), "Deleted user!");
    }

    @Test
    public void testDeleteUser_NotFound() {
        doThrow(new EntityNotFoundException("User not found!")).when(userService).deleteUser(any(UserDTO.class));

        ResponseEntity<String> response = userController.deleteUser(userDTO);

        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert Objects.equals(response.getBody(), "User not found!");
    }
}