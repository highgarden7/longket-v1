package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.challenge.ChallengeLine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class ChallengeLineDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createChallengeLineRequest {
        @Schema(description = "스킬 ID", example = "1")
        private Long skillId;

        @Schema(description = "카테고리타입", example = "foot_trick")
        private String categoryType;

        public ChallengeLine toEntity() {
            return ChallengeLine.builder()
                    .skillId(this.skillId)
                    .categoryType(this.categoryType)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeLineResponse {
        @Schema(description = "스킬 ID", example = "1")
        private Long skillId;

        @Schema(description = "카테고리타입", example = "foot_trick")
        private String categoryType;

        @Schema(description = "order_num", example = "1")
        private int orderNum;

        public static ChallengeLineResponse of(ChallengeLine challengeLine) {
            return ChallengeLineResponse.builder()
                    .skillId(challengeLine.getSkillId())
                    .categoryType(challengeLine.getCategoryType())
                    .orderNum(challengeLine.getOrderNum())
                    .build();
        }
    }
}
