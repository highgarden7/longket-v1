package com.longketdan.longket.v1.service.skill;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.config.jwt.JwtBody;
import com.longketdan.longket.v1.model.entity.skill.FavoriteTrick;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.repository.skill.FavoriteTrickRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteTrickService {

    private final FavoriteTrickRepository favoriteTrickRepository;

    private final UserRepository userRepository;

    public void createFavoriteTrick(Long skillId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        favoriteTrickRepository.save(FavoriteTrick.builder()
                .trickId(skillId)
                .userId(user.getId())
                .categoryType(categoryType)
                .build());
    }

    public List<FavoriteTrick> getFavoriteTrickList(String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return favoriteTrickRepository.findByUserIdAndCategoryType(user.getId(), categoryType);
    }
}
