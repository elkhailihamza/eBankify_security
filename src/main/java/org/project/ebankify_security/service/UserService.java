package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.UserDTO;

public interface UserService {
    void deleteUser(UserDTO userDTO);
    UserDTO createUser(UserDTO userDTO);
    UserDTO modifyUser(UserDTO userDTO);
}
