package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.user.UserProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class SkillCommunityDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillCommunityResponse {
        @Schema(description = "유니크 id")
        private Long id;

        @Schema(description = "유저 아이디")
        private Long userId;

        @Schema(description = "좋아요 수")
        private int likeCount;

        @Schema(description = "코멘트 수")
        private int commentCount;

        @Schema(description = "스킬 아이디")
        private Long skillId;

        @Schema(description = "카테고리 타입")
        private String categoryType;

        @Schema(description = "비디오 아이디")
        private Long videoId;

        @Schema(description = "설명")
        private String description;

        @Schema(description = "코멘트 여부")
        private String commentYn;

        @Schema(description = "좋아요 여부")
        private String likeYn;

        public static SkillCommunityResponse of(int likeCount, int commentCount, String likeYn, UserProgress userProgress) {
            return SkillCommunityResponse.builder()
                    .id(userProgress.getId())
                    .userId(userProgress.getUserId())
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .skillId(userProgress.getSkillId())
                    .categoryType(userProgress.getCategoryType())
                    .videoId(userProgress.getVideoId())
                    .description(userProgress.getDescription())
                    .commentYn(userProgress.getCommentYn())
                    .likeYn(likeYn)
                    .build();
        }
    }
}
