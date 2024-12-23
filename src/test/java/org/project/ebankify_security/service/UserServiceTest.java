package org.project.ebankify_security.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.service.implementation.SharedMethodServiceImpl;
import org.project.ebankify_security.service.implementation.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private SharedMethodServiceImpl sharedMethodService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);
        User user = new User();
        user.setPassword("password123");

        given(userMapper.toUser(userDTO)).willReturn(user);
        given(userDAO.save(any(User.class))).willReturn(user);
        given(userMapper.toUserDTO(user)).willReturn(userDTO);
        given(passwordEncoder.encode(user.getPassword())).willReturn("encodedPassword");

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        verify(passwordEncoder).encode("password123");
        verify(userDAO).save(any(User.class));
    }

    @Test
    public void testCreateUser_EmailAlreadyExists() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        given(userDAO.existsUserByEmail(userDTO.getEmail())).willReturn(true);

        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class,
                () -> userService.createUser(userDTO));

        assertEquals("User with same email already exists!", exception.getMessage());
    }

    @Test
    public void testModifyUser() {
        long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        UserDTO userDTO = new UserDTO(userId, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);
        User updatedUser = new User();
        updatedUser.setId(userId);

        given(userDAO.findById(userId)).willReturn(Optional.of(existingUser));
        given(userMapper.toUser(userDTO)).willReturn(updatedUser);
        given(userDAO.save(any(User.class))).willReturn(updatedUser);
        given(userMapper.toUserDTO(updatedUser)).willReturn(userDTO);
        doNothing().when(sharedMethodService).mergeEntityWithEntityDTO(any(User.class), any(User.class));

        UserDTO updatedUserDTO = userService.modifyUser(userDTO);

        assertNotNull(updatedUserDTO);
        assertEquals(userDTO.getId(), updatedUserDTO.getId());
        verify(userDAO).findById(userId);
        verify(sharedMethodService).mergeEntityWithEntityDTO(any(User.class), any(User.class));
        verify(userDAO).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        UserDTO userDTO = new UserDTO(userId, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        given(userDAO.findById(userId)).willReturn(Optional.of(user));
        doNothing().when(userDAO).delete(user);

        userService.deleteUser(userDTO);

        verify(userDAO).findById(userId);
        verify(userDAO).delete(user);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);

        given(userDAO.findById(userDTO.getId())).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(userDTO));

        assertEquals("User not found!", exception.getMessage());
        verify(userDAO).findById(userDTO.getId());
        verify(userDAO, never()).delete(any(User.class));
    }
}