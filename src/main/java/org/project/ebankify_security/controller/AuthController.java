package org.project.ebankify_security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO authDTO) {
        authService.login(authDTO);
        return null;
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated(AuthDTO.register.class) AuthDTO authDTO) {
        authService.register(authDTO);
        return null;
    }
}
