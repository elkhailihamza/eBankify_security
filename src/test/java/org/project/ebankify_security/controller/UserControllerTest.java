package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

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
        MockitoAnnotations.openMocks(this);
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password123");
        userDTO.setAge(25);
        userDTO.setMonthlyIncome(5000.0);
        userDTO.setCreditScore(700);
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

        try {
            userController.createNewUser(userDTO);
        } catch (EmailAlreadyInUseException e) {
            assert e.getMessage().equals("User with same email already exists!");
        }
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

        try {
            userController.modifyUser(1L, userDTO);
        } catch (EntityNotFoundException e) {
            assert e.getMessage().equals("User not found!");
        }
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

        try {
            userController.deleteUser(userDTO);
        } catch (EntityNotFoundException e) {
            assert e.getMessage().equals("User not found!");
        }
    }
}