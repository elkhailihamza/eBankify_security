package org.project.ebankify_security.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.RoleDAO;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.entity.Role;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.exception.EmailAlreadyInUseException;
import org.project.ebankify_security.security.SecurityUser;
import org.project.ebankify_security.service.AuthService;
import org.project.ebankify_security.util.jwt.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class AuthServiceImpl implements AuthService {
    private final UserDAO dao;
    private final RoleDAO roleDao;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(AuthDTO authDTO) {
        User user = dao.findUserByEmail(authDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: User not found!"));

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Error: Invalid password!");
        }

        return jwtUtils.generateTokenFromUsername(new SecurityUser(user));
    }

    @Override
    public void register(AuthDTO authDTO) {
        if (dao.existsUserByEmail(authDTO.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use!");
        }
        Role role = roleDao.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Default role not found!"));
        User user = userMapper.toUser(authDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);

        dao.save(user);
    }

    @Override
    public User toUser(AuthDTO authDTO) {
        return userMapper.toUser(authDTO);
    }

    @Override
    public AuthDTO toAuthDTO(User user) {
        return userMapper.toAuthDTO(user);
    }
}
