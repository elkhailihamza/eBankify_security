package org.project.ebankify_security.controller;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping("/update/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> modifyUser(@PathVariable long userId, @RequestBody UserDTO userDTO) {
        userDTO.setId(userId);
        return ResponseEntity.ok(userService.modifyUser(userDTO));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(UserDTO userDTO) {
        userService.deleteUser(userDTO);
        return ResponseEntity.ok("Deleted user!");
    }

}