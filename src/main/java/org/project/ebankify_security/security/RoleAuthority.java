package org.project.ebankify_security.security;

import org.project.ebankify_security.entity.Role;
import org.springframework.security.core.GrantedAuthority;

public record RoleAuthority(Role role) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return role.getName();
    }
}
