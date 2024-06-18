package com.longketdan.longket.v1.service.skill;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.skill.Dancing;
import com.longketdan.longket.v1.model.entity.skill.DancingCategory;
import com.longketdan.longket.v1.repository.skill.DancingCategoryRepository;
import com.longketdan.longket.v1.repository.skill.DancingRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DancingService {
    private final DancingRepository dancingRepository;

    private final DancingCategoryRepository dancingCategoryRepository;

    public Dancing getDancingById(Long id) {
        return dancingRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
    public int getDancingCountByDancingCategoryId(Long dancingCategoryId) {
        return dancingRepository.countByDancingCategoryId(dancingCategoryId);
    }

    public List<Dancing> getDancingByOriginalName(String originalName) {
        return dancingRepository.findByOriginalNameAndIsEnable(originalName, true);
    }

    public Page<DancingCategory> getDancingList(int page,
                                                int pageSize,
                                                String keyword,
                                                String aliasName,
                                                String drivingDirection,
                                                String footPosition,
                                                String carving,
                                                Long steps,
                                                Long bodyDegree,
                                                String difficulty) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Specification<DancingCategory> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 키워드 조건이 있는 경우
            if (keyword != null && !keyword.isEmpty()) {
                Predicate korNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("originalKorName")), "%" + keyword + "%");
                Predicate engNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("originalKorName")), "%" + keyword + "%");

                predicates.add(criteriaBuilder.or(korNamePredicate, engNamePredicate));
            }

            // aliasName
            if (aliasName != null) {
                predicates.add(criteriaBuilder.equal(root.get("aliasName"), aliasName));
            }

            // 주행방향
            if (drivingDirection != null && !drivingDirection.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("drivingDirection"), drivingDirection));
            }

            // 풋 포지션
            if (footPosition != null && !footPosition.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("footPosition"), footPosition));
            }

            // 카빙
            if (carving != null && !carving.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("carving"), carving));
            }

            // 스텝
            if (steps != null) {
                predicates.add(criteriaBuilder.equal(root.get("steps"), steps));
            }

            // 몸 회전각도
            if (bodyDegree != null) {
                predicates.add(criteriaBuilder.equal(root.get("bodyDegree"), bodyDegree));
            }

            // 난이도
            if (difficulty != null && !difficulty.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("difficulty"), difficulty));
            }

            predicates.add(criteriaBuilder.equal(root.get("isEnable"), true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return dancingCategoryRepository.findAll(spec, pageable);
    }
}
