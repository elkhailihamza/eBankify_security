package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.AuthDTO;

public interface AuthService {
    void login(AuthDTO authDTO);
    void register(AuthDTO authDTO);
}
