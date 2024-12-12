package org.project.ebankify_security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserDAO userDao;

    @Mock
    private PasswordEncoder passwordEncoder; // Mock for password encoding

    @InjectMocks
    private UserService userService;

    private UserReqDto userReqDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Role userRole = new Role();
        userRole.setName("USER");
        userReqDto = new UserReqDto("John", "Doe", "john.doe@example.com", "password123", 25, Set.of(userRole));
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setId(1L);

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userDao.save(any(User.class))).thenReturn(user);

        User convertedUser = userService.toUser(userReqDto); // Convert UserReqDto to User
        User savedUser = userService.saveUser(convertedUser); // Save the converted User
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFindUserById() {
        User user = new User();
        user.setId(1L);

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> userOpt = userService.findUserById(1L);
        assertTrue(userOpt.isPresent());
        assertEquals(1L, userOpt.get().getId());
    }

    @Test
    public void testUserExistsByEmail() {
        when(userDao.existsUserByEmail("john.doe@example.com")).thenReturn(true);

        boolean exists = userService.userExistsByEmail("john.doe@example.com");
        assertTrue(exists);
    }

    @Test
    public void testToUser() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        User user = userService.toUser(userReqDto);
        assertNotNull(user);
        assertEquals(userReqDto.getEmail(), user.getEmail());
        assertEquals("encodedPassword", user.getPassword()); // Ensure password is encoded
    }
}