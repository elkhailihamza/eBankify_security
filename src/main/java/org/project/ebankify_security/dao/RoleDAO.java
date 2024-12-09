package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
