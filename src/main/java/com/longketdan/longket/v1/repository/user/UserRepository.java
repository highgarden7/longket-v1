package com.longketdan.longket.v1.repository.user;

import com.longketdan.longket.v1.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    Optional<User> findByNickName(String nickName);

    Page<User> findAllByDelYn(String delYn, PageRequest pageRequest);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByEmailAndDelYn(String email, String delYn);

}
