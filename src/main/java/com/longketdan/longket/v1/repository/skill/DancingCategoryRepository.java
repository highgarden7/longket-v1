package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.DancingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DancingCategoryRepository extends JpaRepository<DancingCategory, Long>, JpaSpecificationExecutor<DancingCategory> {
}
