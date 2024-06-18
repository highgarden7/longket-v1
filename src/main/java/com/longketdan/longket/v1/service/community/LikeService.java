package com.longketdan.longket.v1.service.community;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.community.Like;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.repository.community.LikeRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.util.Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    private final UserRepository userRepository;


    public int getLikeCount(Long progressId, String categoryType) {
        return likeRepository.countByTargetIdAndCategoryType(progressId, categoryType);
    }
    public int createLike(Like like) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        like.setUserId(user.getId());
        likeRepository.save(like);

        return likeRepository.countByTargetIdAndCategoryType(like.getTargetId(), like.getCategoryType());
    }

    public boolean isLike(Long targetId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return likeRepository.existsByTargetIdAndUserIdAndCategoryType(targetId, user.getId(), categoryType);
    }

    @Transactional
    public int deleteLike(Long targetId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        likeRepository.deleteByUserIdAndTargetIdAndCategoryType(user.getId(), targetId, categoryType);

        return likeRepository.countByTargetIdAndCategoryType(targetId, categoryType);
    }
}
