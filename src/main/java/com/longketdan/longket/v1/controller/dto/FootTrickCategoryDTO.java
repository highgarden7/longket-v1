package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.skill.FootTrickCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class FootTrickCategoryDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class footTrickCategoryResponse {
        @Schema(description = "트릭 카테고리 unique Id")
        private Long id;

        @Schema(description = "트릭 original 한글 이름 ex) 샤빗")
        private String originalKorName;

        @Schema(description = "트릭 original 영어 이름 ex) Shove it")
        private String originalEngName;

        @Schema(description = "트릭 별칭... ex) 샤빗 레인보우")
        private String aliasName;

        @Schema(description = "스탭 수? ex) 0, 1, 2 ...")
        private Long footPlant;

        @Schema(description = "플립 종류 ex) kick | heel | pressure ...")
        private String flip;

        @Schema(description = "트릭 난이도 ex) 초급, 중급 ...")
        private String difficulty;

        @Schema(description = "보드 회전각도 ex) 180, 360...")
        private Long boardDegree;

        @Schema(description = "몸 회전각도... ex) 180, 360...")
        private Long bodyDegree;

        @Schema(description = "썸네일 unique Id")
        private Long thumbNailId;

        @Schema(description = "유저가 등록한 트릭갯수")
        private int userTrickCount;

        @Schema(description = "해당 카테고리에 속한 트릭 수")
        private int trickCount;

        public static FootTrickCategoryDTO.footTrickCategoryResponse of(FootTrickCategory footTrickCategory, int userTrickCount, int trickCount) {
            return footTrickCategoryResponse.builder()
                    .id(footTrickCategory.getId())
                    .originalEngName(footTrickCategory.getOriginalEngName())
                    .originalKorName(footTrickCategory.getOriginalKorName())
                    .aliasName(footTrickCategory.getAliasName())
                    .footPlant(footTrickCategory.getFootPlant())
                    .flip(footTrickCategory.getFlip())
                    .difficulty(footTrickCategory.getDifficulty())
                    .boardDegree(footTrickCategory.getBoardDegree())
                    .bodyDegree(footTrickCategory.getBodyDegree())
                    .thumbNailId(footTrickCategory.getThumbNailId())
                    .userTrickCount(userTrickCount)
                    .trickCount(trickCount)
                    .build();
        }
    }
}
