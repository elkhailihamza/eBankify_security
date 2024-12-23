package org.project.ebankify_security.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateNewUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        // Mock the service call
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // Call the controller method
        ResponseEntity<UserDTO> response = userController.createNewUser(userDTO);

        // Verify interaction with service
        verify(userService, times(1)).createUser(any(UserDTO.class));

        // Check response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testModifyUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        // Mock the service call
        when(userService.modifyUser(any(UserDTO.class))).thenReturn(userDTO);

        // Call the controller method
        ResponseEntity<UserDTO> response = userController.modifyUser(1L, userDTO);

        // Verify interaction with service
        verify(userService, times(1)).modifyUser(any(UserDTO.class));

        // Check response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testDeleteUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        // Mock the service call
        doNothing().when(userService).deleteUser(any(UserDTO.class));

        // Call the controller method
        ResponseEntity<String> response = userController.deleteUser(userDTO);

        // Verify interaction with service
        verify(userService, times(1)).deleteUser(any(UserDTO.class));

        // Check response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted user!", response.getBody());
    }
}
