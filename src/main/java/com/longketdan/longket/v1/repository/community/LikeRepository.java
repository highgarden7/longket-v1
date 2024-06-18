package com.longketdan.longket.v1.repository.community;

import com.longketdan.longket.v1.model.entity.community.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByTargetIdAndCategoryType(Long targetId, String categoryType);

    void deleteByUserIdAndTargetIdAndCategoryType(Long userId, Long userProgressId, String categoryType);
    boolean existsByTargetIdAndUserIdAndCategoryType(Long userProgressId, Long userId, String categoryType);
}
