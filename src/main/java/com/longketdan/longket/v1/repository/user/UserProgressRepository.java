package com.longketdan.longket.v1.repository.user;

import com.longketdan.longket.v1.model.entity.user.UserProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, String> {
    Optional<UserProgress> findById(Long userProgress);

    List<UserProgress> findByUserIdAndCategoryIdAndSkillIdAndCategoryTypeOrderByIdDesc(Long userId, Long categoryId, Long skillId, String categoryType);

    Page<UserProgress> findByCategoryIdAndSkillIdAndStatusAndOpenYnAndCategoryTypeAndUserIdNotOrderByUpdateDateDesc(Long categoryId, Long skillId, String status, String openYn, String categoryType, Long userId, Pageable pageable);

    Page<UserProgress> findByUserIdAndCategoryType(Long userId, String categoryType, Pageable pageable);

    Page<UserProgress> findByUserIdAndOpenYnOrderByCreateDateDesc(Long userId, String openYn, Pageable pageable);

    Page<UserProgress> findByUserIdAndCategoryTypeAndOpenYn(Long userId, String categoryType, String openYn, Pageable pageable);

    Page<UserProgress> findByOpenYnAndStatusOrderByUpdateDateDesc(String openYn, String status, Pageable pageable);

    Page<UserProgress> findByCategoryTypeAndSkillIdAndOpenYn(String categoryType, Long skillId, String openYn, Pageable pageable);

    Page<UserProgress> findByCategoryTypeAndOpenYn(String categoryType, String openYn, Pageable pageable);

    int countByUserIdAndCategoryTypeAndStatus(Long userId, String categoryType, String status);

    int countByUserIdAndCategoryIdAndCategoryType(Long userId, Long categoryId, String categoryType);

    int countByUserIdAndCategoryIdAndCategoryTypeAndStatus(Long userId, Long categoryId, String categoryType, String status);

    Page<UserProgress> findByStatusOrderByCreateDateAsc(String status, Pageable pageable);
}
