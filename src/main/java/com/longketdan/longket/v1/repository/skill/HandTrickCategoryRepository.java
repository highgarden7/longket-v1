package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.HandTrickCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HandTrickCategoryRepository extends JpaRepository<HandTrickCategory, Long>, JpaSpecificationExecutor<HandTrickCategory> {
}
