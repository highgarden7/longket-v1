package com.longketdan.longket.v1.repository;

import com.longketdan.longket.v1.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, String> {
    Optional<Resource> findById(Long id);
}
