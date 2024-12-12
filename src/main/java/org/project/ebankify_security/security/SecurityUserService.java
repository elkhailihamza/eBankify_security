package org.project.ebankify_security.security;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.dao.UserDAO;
import org.project.ebankify_security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new SecurityUser(user);
    }
}
