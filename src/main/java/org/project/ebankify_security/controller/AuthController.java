package org.project.ebankify_security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.AuthTokenResponseDTO;
import org.project.ebankify_security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponseDTO> login(@RequestBody @Valid AuthDTO authDTO) {
        AuthTokenResponseDTO tokenDTO = authService.login(authDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenDTO.getToken())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated(AuthDTO.Register.class) AuthDTO authDTO) {
        authService.register(authDTO);
        return null;
    }
}
