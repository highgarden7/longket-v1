package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.community.Like;
import com.longketdan.longket.v1.model.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class LikeDTO {

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeRequest {
        @Schema(description = "target Id")
        private Long targetId;

        @Schema(description = "카테고리 타입")
        private String categoryType;

        public static Like toEntity(LikeRequest likeRequest) {
            return Like.builder()
                    .targetId(likeRequest.targetId)
                    .categoryType(likeRequest.getCategoryType())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponse {
        @Schema(description = "좋아요 unique Id")
        private Long likeId;

        @Schema(description = "유저 unique Id")
        private Long userId;

        @Schema(description = "target Id")
        private Long targetId;

        @Schema(description = "카테고리 타입")
        private String categoryType;

        @Schema(description = "유저 이름")
        private String nickName;

        @Schema(description = "유저 프로필 이미지 unique Id")
        private Long profileImageId;

        public static LikeResponse of(Like like, User user) {
            return LikeResponse.builder()
                    .likeId(like.getId())
                    .userId(like.getUserId())
                    .nickName(user.getNickName())
                    .profileImageId(user.getProfileImgId())
                    .targetId(like.getTargetId())
                    .categoryType(like.getCategoryType())
                    .build();
        }
    }
}
