package com.longketdan.longket.v1.repository.user;

import com.longketdan.longket.v1.model.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByUserId(Long userId);
}
