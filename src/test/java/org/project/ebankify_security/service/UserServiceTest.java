package org.project.ebankify_security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.entity.Role;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.service.implementation.UserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDao;

    @Mock
    private PasswordEncoder passwordEncoder; // Mock for password encoding

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Role userRole = new Role();
        userRole.setName("USER");
        userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password123", 25, 5000.0, 700);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setId(1L);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(user);
        when(userDao.existsUserByEmail(any(String.class))).thenReturn(false);
        when(userDao.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    public void testCreateUserWithEmailAlreadyExists() {
        when(userDao.existsUserByEmail(any(String.class))).thenReturn(true);

        Exception exception = assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(userDTO));

        assertEquals("User with same email already exists!", exception.getMessage());
    }

    @Test
    public void testModifyUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        when(userDao.findById(anyLong())).thenReturn(Optional.of(existingUser));

        when(userMapper.toUser(any(UserDTO.class))).thenReturn(existingUser);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO updatedUser = userService.modifyUser(userDTO);

        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getId());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(userDTO);

        // Verify that the delete method was called on the userDao
        verify(userDao).delete(user);
    }
}
