package org.project.ebankify_security.service;

import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;

import java.util.Optional;

public interface UserService {
    User toUser(UserReqDto userReqDto);
    Optional<User> findUserById(long id);
    User saveUser(User user);
    void deleteUser(User user);
    boolean userExistsByEmail(String email);
}
