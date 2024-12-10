package org.project.ebankify_security.util.auth;

import org.project.ebankify_security.security.SecurityUser;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    public static SecurityUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (SecurityUser) authentication.getPrincipal();
        }
        throw new AuthenticationCredentialsNotFoundException("User not authenticated!");
    }

    public static String getAuthenticationUsername() {
        SecurityUser securityUser = getAuthenticatedUser();
        if (securityUser != null && securityUser.getUsername() != null) {
            return securityUser.getUsername();
        }
        throw new UsernameNotFoundException("Email not found!");
    }
}
