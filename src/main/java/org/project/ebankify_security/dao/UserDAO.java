package org.project.ebankify_security.dao;

import org.project.ebankify_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}
