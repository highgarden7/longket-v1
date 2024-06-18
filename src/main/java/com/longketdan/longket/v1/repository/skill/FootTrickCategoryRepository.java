package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.FootTrickCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FootTrickCategoryRepository extends JpaRepository<FootTrickCategory, Long>, JpaSpecificationExecutor<FootTrickCategory> {
}
