package com.longketdan.longket.v1.application;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.*;
import com.longketdan.longket.v1.model.entity.community.Comment;
import com.longketdan.longket.v1.model.entity.user.UserProgress;
import com.longketdan.longket.v1.service.community.CommentService;
import com.longketdan.longket.v1.service.community.LikeService;
import com.longketdan.longket.v1.service.skill.DancingService;
import com.longketdan.longket.v1.service.skill.FootTrickService;
import com.longketdan.longket.v1.service.skill.HandTrickService;
import com.longketdan.longket.v1.service.user.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommunityApplicationService {
    private final FootTrickService footTrickService;

    private final HandTrickService handTrickService;

    private final DancingService dancingService;

    private final UserProgressService userProgressService;

    private final CommentService commentService;

    private final LikeService likeService;

    public Page<SkillCommunityDTO.SkillCommunityResponse> getCommunityList(int page, int pageSize) {
        Page<UserProgress> userProgressList = userProgressService.getAllUserProgress(page, pageSize);

        return userProgressList.map(userProgress -> {
            int likeCount = likeService.getLikeCount(userProgress.getId(), "skill");
            int commentCount = commentService.getCommentCountByUserProgressId(userProgress.getId());
            String likeYn = likeService.isLike(userProgress.getId(), "skill") ? "Y" : "N";
            return SkillCommunityDTO.SkillCommunityResponse.of(likeCount, commentCount, likeYn, userProgress);
        });
    }

    public Page<SkillCommunityDTO.SkillCommunityResponse> getCommunityDetailList(int page, int pageSize, Long categoryId, Long skillId, String categoryType) {
        Page<UserProgress> userProgressList = userProgressService.getProgressDetailList(page, pageSize, categoryId, skillId, categoryType);

        return userProgressList.map(userProgress -> {
            int likeCount = likeService.getLikeCount(userProgress.getId(), "skill");
            int commentCount = commentService.getCommentCountByUserProgressId(userProgress.getId());
            String likeYn = likeService.isLike(userProgress.getId(), "skill") ? "Y" : "N";
            return SkillCommunityDTO.SkillCommunityResponse.of(likeCount, commentCount, likeYn, userProgress);
        });
    }

    public Page<SkillCommunityDTO.SkillCommunityResponse> getCommunityDetailListByUserId(int page, int pageSize, Long userId) {
        Page<UserProgress> userProgressList = userProgressService.getUserProgressByUserId(page, pageSize, userId);

        return userProgressList.map(userProgress -> {
            int likeCount = likeService.getLikeCount(userProgress.getId(), "skill");
            int commentCount = commentService.getCommentCountByUserProgressId(userProgress.getId());
            String likeYn = likeService.isLike(userProgress.getId(), "skill") ? "Y" : "N";
            return SkillCommunityDTO.SkillCommunityResponse.of(likeCount, commentCount, likeYn, userProgress);
        });
    }

    public Page<SkillCommunityDTO.SkillCommunityResponse> getSkillByUserIdAndType(int page, int pageSize, Long userId, String categoryType) {
        Page<UserProgress> userProgressList = userProgressService.getUserSkillProgressByCategoryType(page, pageSize, userId, categoryType);

        return userProgressList.map(userProgress -> {
            int likeCount = likeService.getLikeCount(userProgress.getId(), categoryType);
            int commentCount = commentService.getCommentCountByUserProgressId(userProgress.getId());
            String likeYn = likeService.isLike(userProgress.getId(), categoryType) ? "Y" : "N";
            return SkillCommunityDTO.SkillCommunityResponse.of(likeCount, commentCount, likeYn, userProgress);
        });
    }

    public Page<SkillCommunityDTO.SkillCommunityResponse> getSkillBySkillIdAndType(int page, int pageSize, Long skillId, String categoryType) {
        Page<UserProgress> userProgressList = userProgressService.getAllUserSkillProgressBySkillIdAndCategoryType(page, pageSize, skillId, categoryType);

        return userProgressList.map(userProgress -> {
            int likeCount = likeService.getLikeCount(userProgress.getId(), categoryType);
            int commentCount = commentService.getCommentCountByUserProgressId(userProgress.getId());
            String likeYn = likeService.isLike(userProgress.getId(), categoryType) ? "Y" : "N";
            return SkillCommunityDTO.SkillCommunityResponse.of(likeCount, commentCount, likeYn, userProgress);
        });
    }

    public Page<CommentDTO.CommentResponse> getCommentListByTargetId(int page, int pageSize, Long targetId) {
        Page<Comment> commentList = commentService.getCommentByTargetIdAndParentCommentIdIsNull(page, pageSize, targetId);

        return commentList.map(comment -> {
            boolean hasChild = commentService.hasChild(comment.getId());
            return CommentDTO.CommentResponse.of(comment, hasChild);
        });
    }

    public Page<CommentDTO.CommentResponse> getCommentListByParentCommentId(int page, int pageSize, Long parentCommentId) {
        Page<Comment> commentList = commentService.getCommentByParentCommentId(page, pageSize, parentCommentId);

        return commentList.map(comment -> CommentDTO.CommentResponse.of(comment, false));
    }

    public Object getSkillDetail(Long skillId, String categoryType) {
        return switch (categoryType) {
            case "hand_trick" -> HandTrickDTO.trickResponse.of(handTrickService.getHandTrickById(skillId));
            case "foot_trick" -> FootTrickDTO.trickResponse.of(footTrickService.getFootTrickById(skillId));
            case "dancing" -> DancingDTO.dancingResponse.of(dancingService.getDancingById(skillId));
            default -> throw new CustomException(ErrorCode.INVALID_CATEGORY_TYPE);
        };
    }
}
