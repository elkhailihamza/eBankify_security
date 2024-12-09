package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;
    private final UserMapper userMapper;

    public User toUser(UserReqDto userReqDto) {
        return userMapper.toUser(userReqDto);
    }

    public Optional<User> findUserById(long id) {
        return userDao.findById(id);
    }

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public boolean userExistsByEmail(String email) {
        return userDao.existsUserByEmail(email);
    }
}
