package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.FootTrick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FootTrickRepository extends JpaRepository<FootTrick, Long>, JpaSpecificationExecutor<FootTrick> {
    Optional<FootTrick> findById(Long id);
    List<FootTrick> findByOriginalNameAndIsEnable(String originalName, Boolean isEnable);

    int countByFootTrickCategoryId(Long footTrickCategoryId);

    int countByIsEnable(Boolean isEnable);
}
