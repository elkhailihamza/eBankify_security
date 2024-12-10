package org.project.ebankify_security.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.exception.UnexpectedErrorException;
import org.project.ebankify_security.service.UserService;
import org.project.ebankify_security.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private Role role;

    private UserReqDto userReqDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userReqDto = new UserReqDto("John", "Doe", "john.doe@example.com", "password123", 25, roles);
    }

    @Test
    public void testCreateNewUser() {
        User user = new User();
        user.setId(1L);

        when(userService.userExistsByEmail(userReqDto.getEmail())).thenReturn(false);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<String> response = userController.createNewUser(userReqDto);

        verify(userService, times(1)).saveUser(any(User.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert Objects.equals(response.getBody(), "User created: id - 1");
    }

    @Test
    public void testCreateNewUser_Conflict() {
        when(userService.userExistsByEmail(userReqDto.getEmail())).thenReturn(true);

        try {
            userController.createNewUser(userReqDto);
        } catch (EmailAlreadyInUseException e) {
            assert e.getMessage().equals("User with same email already exists!");
        }
    }

    @Test
    public void testModifyUser() {
        User existingUser = new User();
        existingUser.setId(1L);

        when(userService.findUserById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userService.userExistsByEmail(userReqDto.getEmail())).thenReturn(false);
        when(userService.saveUser(any(User.class))).thenReturn(existingUser);

        ResponseEntity<String> response = userController.modifyUser(1L, userReqDto);

        assert response.getStatusCode() == HttpStatus.CREATED;
        assert Objects.equals(response.getBody(), "User modified: id - 1");
    }

    @Test
    public void testModifyUser_UnexpectedError() {
        when(userService.findUserById(anyLong())).thenReturn(Optional.empty());

        try {
            userController.modifyUser(1L, userReqDto);
        } catch (UnexpectedErrorException e) {
            assert e.getMessage().equals("An error has occurred!");
        }
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userId")).thenReturn("1"); // Mocking userId retrieval

        // Act
        ResponseEntity<String> response = userController.deleteUser(request);

        // Assert
        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(response.getBody(), "Deleted User!");
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userService.findUserById(anyLong())).thenReturn(Optional.empty());

        try {
            userController.deleteUser(mock(HttpServletRequest.class));
        } catch (EntityNotFoundException e) {
            assert e.getMessage().equals("User not found!");
        }
    }
}
