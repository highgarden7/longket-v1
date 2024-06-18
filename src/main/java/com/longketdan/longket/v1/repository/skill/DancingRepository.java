package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.Dancing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DancingRepository extends JpaRepository<Dancing, Long>, JpaSpecificationExecutor<Dancing> {
    Optional<Dancing> findById(Long id);
    List<Dancing> findByOriginalNameAndIsEnable(String originalName, Boolean isEnable);

    int countByDancingCategoryId(Long dancingCategoryId);

    int countByIsEnable(Boolean isEnable);
}
