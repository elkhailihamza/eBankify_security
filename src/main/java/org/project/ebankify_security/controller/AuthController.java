package org.project.ebankify_security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.AuthTokenResponse;
import org.project.ebankify_security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody @Valid AuthDTO authDTO) {
        String token = authService.login(authDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated(AuthDTO.Register.class) AuthDTO authDTO) {
        authService.register(authDTO);
        return null;
    }
}
