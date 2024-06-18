package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.user.UserProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public class UserProgressDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserFootTrickRequest {
        private Long footTrickCategoryId;

        private Long footTrickId;

        private Long videoId;

        private String description;

        private String openYn;

        private String commentYn;

        public UserProgress toEntity() {
            return UserProgress.builder()
                    .categoryId(this.footTrickCategoryId)
                    .skillId(this.footTrickId)
                    .videoId(this.videoId)
                    .categoryType("foot_trick")
                    .description(this.description)
                    .openYn(this.openYn)
                    .commentYn(this.commentYn)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserHandTrickRequest {
        private Long handTrickCategoryId;

        private Long handTrickId;

        private Long videoId;

        private String description;

        private String openYn;

        private String commentYn;

        public UserProgress toEntity() {
            return UserProgress.builder()
                    .categoryId(this.handTrickCategoryId)
                    .skillId(this.handTrickId)
                    .videoId(this.videoId)
                    .categoryType("hand_trick")
                    .description(this.description)
                    .openYn(this.openYn)
                    .commentYn(this.commentYn)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserDancingRequest {
        private Long dancingCategoryId;

        private Long dancingId;

        private Long videoId;

        private String description;

        private String openYn;

        private String commentYn;

        public UserProgress toEntity() {
            return UserProgress.builder()
                    .categoryId(this.dancingCategoryId)
                    .skillId(this.dancingId)
                    .videoId(this.videoId)
                    .categoryType("dancing")
                    .description(this.description)
                    .openYn(this.openYn)
                    .commentYn(this.commentYn)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProgressAdminResponse {
        @Schema(description = "유저 프로그레스 아이디")
        private Long id;

        @Schema(description = "생성일")
        private LocalDateTime createDate;

        @Schema(description = "수정일")
        private LocalDateTime updateDate;

        @Schema(description = "유저 아이디")
        private Long userId;

        @Schema(description = "카테고리 아이디")
        private Long categoryId;

        @Schema(description = "스킬 아이디")
        private Long skillId;

        @Schema(description = "카테고리 타입")
        private String categoryType;

        @Schema(description = "비디오 아이디")
        private Long videoId;

        @Schema(description = "설명")
        private String description;

        @Schema(description = "댓글 여부")
        private String commentYn;

        @Schema(description = "상태")
        private String status;

        public static UserProgressDTO.UserProgressAdminResponse of(UserProgress userProgress) {
            return UserProgressAdminResponse.builder()
                    .id(userProgress.getId())
                    .createDate(userProgress.getCreateDate())
                    .updateDate(userProgress.getUpdateDate())
                    .userId(userProgress.getUserId())
                    .categoryId(userProgress.getCategoryId())
                    .skillId(userProgress.getSkillId())
                    .categoryType(userProgress.getCategoryType())
                    .videoId(userProgress.getVideoId())
                    .description(userProgress.getDescription())
                    .status(userProgress.getStatus())
                    .commentYn(userProgress.getCommentYn())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSkillProgressResponse {
        @Schema(description = "풋트릭 카운트")
        private int footTrickCount;

        @Schema(description = "핸드트릭 카운트")
        private int handTrickCount;

        @Schema(description = "댄싱 카운트")
        private int dancingCount;

        @Schema(description = "진행도 카운트")
        private int progressCount;

        public static UserProgressDTO.UserSkillProgressResponse of(int footTrickCount, int handTrickCount, int dancingCount) {
            return UserSkillProgressResponse.builder()
                    .footTrickCount(footTrickCount)
                    .handTrickCount(handTrickCount)
                    .dancingCount(dancingCount)
                    .progressCount(footTrickCount + handTrickCount + dancingCount)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillProgressResponse {
        @Schema(description = "풋트릭 카운트")
        private int totalFootTrickCount;

        @Schema(description = "핸드트릭 카운트")
        private int totalHandTrickCount;

        @Schema(description = "댄싱 카운트")
        private int totalDancingCount;
        public static UserProgressDTO.SkillProgressResponse of(int totalFootTrickCount, int totalHandTrickCount, int totalDancingCount) {
            return SkillProgressResponse.builder()
                    .totalFootTrickCount(totalFootTrickCount)
                    .totalHandTrickCount(totalHandTrickCount)
                    .totalDancingCount(totalDancingCount)
                    .build();
        }
    }
}
