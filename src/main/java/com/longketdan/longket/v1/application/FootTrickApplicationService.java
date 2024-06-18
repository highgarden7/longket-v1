package com.longketdan.longket.v1.application;

import com.longketdan.longket.config.jwt.JwtBody;
import com.longketdan.longket.v1.controller.dto.FootTrickCategoryDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.skill.FootTrickCategory;
import com.longketdan.longket.v1.service.skill.FootTrickService;
import com.longketdan.longket.v1.service.user.UserProgressService;
import com.longketdan.longket.v1.service.user.UserService;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FootTrickApplicationService {
    private final UserProgressService userProgressService;

    private final FootTrickService footTrickService;

    private final UserService userService;

    public Page<FootTrickCategoryDTO.footTrickCategoryResponse> getTrickList(int page,
                                                                             int pageSize,
                                                                             String keyword,
                                                                             String aliasName,
                                                                             Long footPlant,
                                                                             String flip,
                                                                             String difficulty,
                                                                             Long bodyDegree,
                                                                             Long boardDegree) {
        User user = userService.getUserById(Util.getUserIdByToken());

        Page<FootTrickCategory> trickList = footTrickService.getFootTrickList(page, pageSize, keyword, aliasName, footPlant, flip, difficulty, bodyDegree, boardDegree);

        Page<FootTrickCategoryDTO.footTrickCategoryResponse> dto = trickList.map(trick -> {
            int userTrickCount = userProgressService.getUserFootTrickCountWithCategory(user.getId(), trick.getId());
            int trickCount = footTrickService.getFootTrickCountByFootTrickCategoryId(trick.getId()); // 예시를 위한 가정된 값

            // DTO 변환
            return FootTrickCategoryDTO.footTrickCategoryResponse.of(trick, userTrickCount, trickCount);
        });

        return dto;
    }
}
