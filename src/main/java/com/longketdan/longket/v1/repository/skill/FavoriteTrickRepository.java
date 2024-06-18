package com.longketdan.longket.v1.repository.skill;

import com.longketdan.longket.v1.model.entity.skill.FavoriteTrick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteTrickRepository extends JpaRepository<FavoriteTrick, Long> {
    List<FavoriteTrick> findByUserIdAndCategoryType(Long userId, String categoryType);
}
