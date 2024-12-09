package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.AuthTokenResponseDTO;
import org.project.ebankify_security.entity.User;

public interface AuthService {
    AuthTokenResponseDTO login(AuthDTO authDTO);
    void register(AuthDTO authDTO);
    User toUser(AuthDTO authDTO);
    AuthDTO toAuthDTO(User user);
}
