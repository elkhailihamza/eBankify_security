package org.project.ebankify_security.util;

import lombok.RequiredArgsConstructor;
import org.project.ebankify_security.security.SecurityUser;
import org.project.ebankify_security.security.SecurityUserService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final SecurityUserService securityUserService;

    public SecurityUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String email = jwt.getClaim("email");
            return securityUserService.loadUserByUsername(email);
        }
        throw new AuthenticationCredentialsNotFoundException("User not authenticated!");
    }

    public Object getAuthenticationUsername() {
        SecurityUser securityUser = getAuthenticatedUser();
        if (securityUser != null && securityUser.getUsername() != null) {
            return securityUser.getUsername();
        }
        throw new UsernameNotFoundException("Email not found!");
    }

    public Object getAuthenticationId() {
        SecurityUser securityUser = getAuthenticatedUser();
        if (securityUser != null) {
            return securityUser.getId();
        }
        throw new RuntimeException("User not authenticated!"); // change to a handled runtime exception
    }
}
