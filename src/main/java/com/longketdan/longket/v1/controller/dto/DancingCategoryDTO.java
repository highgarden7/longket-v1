package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.skill.DancingCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class DancingCategoryDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class dancingCategoryResponse {
        @Schema(description = "트릭 카테고리 unique Id")
        private Long id;

        @Schema(description = "트릭 original 한글 이름 ex) 샤빗")
        private String originalKorName;

        @Schema(description = "트릭 original 영어 이름 ex) Shove it")
        private String originalEngName;

        @Schema(description = "트릭 별칭... ex) 샤빗 레인보우")
        private String aliasName;

        @Schema(description = "드라이빙")
        private String driving;

        @Schema(description = "풋 포지션")
        private String footPosition;

        @Schema(description = "트릭 난이도 ex) 초급, 중급 ...")
        private String difficulty;

        @Schema(description = "스탠")
        private String stance;

        @Schema(description = "카빙")
        private String carving;

        @Schema(description = "보드 사이드")
        private String boardSide;

        @Schema(description = "몸 회전각도")
        private Long bodyDegree;

        @Schema(description = "스텝 수")
        private Long steps;

        @Schema(description = "썸네일 unique Id")
        private Long thumbNailId;

        @Schema(description = "유저가 등록한 트릭갯수")
        private int userTrickCount;

        @Schema(description = "해당 카테고리에 속한 트릭 수")
        private int trickCount;

        public static DancingCategoryDTO.dancingCategoryResponse of(DancingCategory dancingCategory, int userTrickCount, int trickCount) {
            return dancingCategoryResponse.builder()
                    .id(dancingCategory.getId())
                    .originalEngName(dancingCategory.getOriginalEngName())
                    .originalKorName(dancingCategory.getOriginalKorName())
                    .aliasName(dancingCategory.getAliasName())
                    .footPosition(dancingCategory.getFootPosition())
                    .carving(dancingCategory.getCarving())
                    .bodyDegree(dancingCategory.getBodyDegree())
                    .steps(dancingCategory.getSteps())
                    .difficulty(dancingCategory.getDifficulty())
                    .thumbNailId(dancingCategory.getThumbNailId())
                    .userTrickCount(userTrickCount)
                    .trickCount(trickCount)
                    .build();
        }
    }
}
