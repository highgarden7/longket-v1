package com.longketdan.longket.v1.repository.user;

import com.longketdan.longket.v1.model.entity.user.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, String>, JpaSpecificationExecutor<UserFollow> {
    Page<UserFollow> findByUserId(Long userId, Pageable pageable);

    Page<UserFollow> findByFollowId(Long followId, Pageable pageable);

    Optional<UserFollow> findByUserIdAndFollowId(Long userId, Long followId);

    boolean existsByUserIdAndFollowId(Long userId, Long followId);
}
