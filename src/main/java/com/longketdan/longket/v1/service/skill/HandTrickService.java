package com.longketdan.longket.v1.service.skill;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.skill.HandTrick;
import com.longketdan.longket.v1.model.entity.skill.HandTrickCategory;
import com.longketdan.longket.v1.repository.skill.HandTrickCategoryRepository;
import com.longketdan.longket.v1.repository.skill.HandTrickRepository;
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
public class HandTrickService {
    private final HandTrickRepository handTrickRepository;

    private final HandTrickCategoryRepository handTrickCategoryRepository;


    public HandTrick getHandTrickById(Long id) {
        return handTrickRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
    public int getHandTrickCountByFootTrickCategoryId(Long handTrickCategoryId) {
        return handTrickRepository.countByHandTrickCategoryId(handTrickCategoryId);
    }

    public List<HandTrick> getTrickByOriginalName(String originalName) {
        return handTrickRepository.findByOriginalNameAndIsEnable(originalName, true);
    }

    public Page<HandTrickCategory> getHandTrickList(int page,
                                                    int pageSize,
                                                    String keyword,
                                                    String aliasName,
                                                    Long footPlant,
                                                    String flip,
                                                    String difficulty,
                                                    Long bodyDegree,
                                                    Long boardDegree) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Specification<HandTrickCategory> spec = (root, query, criteriaBuilder) -> {
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

            // 스텝 수
            if (footPlant != null) {
                predicates.add(criteriaBuilder.equal(root.get("footPlant"), footPlant));
            }

            // 플립 종류
            if (flip != null && !flip.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("flip"), flip));
            }

            // 난이도
            if (difficulty != null && !difficulty.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("difficulty"), difficulty));
            }

            // 몸 회전각도
            if (bodyDegree != null) {
                predicates.add(criteriaBuilder.equal(root.get("bodyDegree"), bodyDegree));
            }

            // 보드 회전각도
            if (boardDegree != null) {
                predicates.add(criteriaBuilder.equal(root.get("boardDegree"), boardDegree));
            }

            predicates.add(criteriaBuilder.equal(root.get("isEnable"), true));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return handTrickCategoryRepository.findAll(spec, pageable);
    }
}
