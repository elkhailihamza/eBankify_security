package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.UserDTO;

public interface KeyCloakService {
    void createUserInKeyCloak(UserDTO userDTO);
}
