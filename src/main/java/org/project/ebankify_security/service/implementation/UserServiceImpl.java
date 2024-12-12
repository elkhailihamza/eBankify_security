package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.exception.UnexpectedErrorException;
import org.project.ebankify_security.service.SharedMethodService;
import org.project.ebankify_security.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SharedMethodService sharedMethodService;

    public void deleteUser(UserDTO userDTO) {
        User user = userDao.findById(userDTO.getId()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userDao.delete(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (!userDao.existsUserByEmail(userDTO.getEmail())) {
            User user = userMapper.toUser(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userMapper.toUserDTO(userDao.save(user));
        }
        throw new EmailAlreadyInUseException("User with same email already exists!");
    }

    @Override
    public UserDTO modifyUser(UserDTO userDTO) {
        User existingUser = userDao.findById(userDTO.getId()).orElseThrow(() -> new UnexpectedErrorException("An error has occurred!"));
        User updatedUser = userMapper.toUser(userDTO);
        sharedMethodService.mergeEntityWithEntityDTO(updatedUser, existingUser);
        return userMapper.toUserDTO(userDao.save(updatedUser));
    }
}
