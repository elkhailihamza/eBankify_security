package org.project.ebankify_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/login")
    public ResponseEntity<?> login() {
        return null;
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register() {
        return null;
    }
}
