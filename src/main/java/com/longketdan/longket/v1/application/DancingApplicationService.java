package com.longketdan.longket.v1.application;

import com.longketdan.longket.v1.controller.dto.DancingCategoryDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.skill.DancingCategory;
import com.longketdan.longket.v1.service.skill.DancingService;
import com.longketdan.longket.v1.service.user.UserProgressService;
import com.longketdan.longket.v1.service.user.UserService;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DancingApplicationService {
    private final UserProgressService userProgressService;

    private final DancingService dancingService;

    private final UserService userService;

    public Page<DancingCategoryDTO.dancingCategoryResponse> getDancingList(int page,
                                                                           int pageSize,
                                                                           String keyword,
                                                                           String aliasName,
                                                                           String drivingDirection,
                                                                           String footPosition,
                                                                           String carving,
                                                                           Long steps,
                                                                            Long bodyDegree,
                                                                           String difficulty) {
        User user = userService.getUserById(Util.getUserIdByToken());

        Page<DancingCategory> dancingList = dancingService.getDancingList(page, pageSize, keyword, aliasName, drivingDirection, footPosition, carving, steps, bodyDegree, difficulty);

        Page<DancingCategoryDTO.dancingCategoryResponse> dto = dancingList.map(dancing -> {
            int userTrickCount = userProgressService.getUserDancingCountWithCategory(user.getId(), dancing.getId());
            int trickCount = dancingService.getDancingCountByDancingCategoryId(dancing.getId()); // 예시를 위한 가정된 값

            // DTO 변환
            return DancingCategoryDTO.dancingCategoryResponse.of(dancing, userTrickCount, trickCount);
        });

        return dto;
    }
}
