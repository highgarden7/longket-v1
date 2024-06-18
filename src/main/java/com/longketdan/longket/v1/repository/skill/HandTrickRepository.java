package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.HandTrick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HandTrickRepository extends JpaRepository<HandTrick, Long>, JpaSpecificationExecutor<HandTrick> {
    Optional<HandTrick> findById(Long id);
    List<HandTrick> findByOriginalNameAndIsEnable(String originalName, Boolean isEnable);

    int countByHandTrickCategoryId(Long handTrickCategoryId);

    int countByIsEnable(Boolean isEnable);
}
