package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.skill.Dancing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class DancingDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class dancingResponse {
        @Schema(description = "트릭 unique Id")
        private Long id;

        @Schema(description = "댄싱 카테고리 unique Id")
        private Long categoryId;

        @Schema(description = "트릭 original 이름 ex) 샤빗")
        private String originalName;

        @Schema(description = "트릭 한글 풀네임 ex) 널리 프론트 샤빗")
        private String korName;

        @Schema(description = "트릭 영어 풀네임 ex) nollie front shove it")
        private String engName;

        @Schema(description = "트릭 별칭... ex) 샤빗 레인보우")
        private String aliasName;

        @Schema(description = "풋 포지션")
        private String footPosition;

        @Schema(description = "트릭 난이도 ex) 초급, 중급 ...")
        private String difficulty;

        @Schema(description = "스탠스")
        private String stance;

        @Schema(description = "카빙")
        private String carving;

        @Schema(description = "몸 회전각도")
        private Long bodyDegree;

        @Schema(description = "보드 사이드")
        private String boardSide;

        @Schema(description = "트릭 설명")
        private String description;

        @Schema(description = "영상 unique Id")
        private Long videoId;

        public static DancingDTO.dancingResponse of(Dancing dancing) {
            return dancingResponse.builder()
                    .id(dancing.getId())
                    .categoryId(dancing.getDancingCategoryId())
                    .originalName(dancing.getOriginalName())
                    .korName(dancing.getKorName())
                    .engName(dancing.getEngName())
                    .aliasName(dancing.getAliasName())
                    .footPosition(dancing.getFootPosition())
                    .stance(dancing.getStance())
                    .carving(dancing.getCarving())
                    .boardSide(dancing.getBoardSide())
                    .bodyDegree(dancing.getBodyDegree())
                    .difficulty(dancing.getDifficulty())
                    .build();
        }
    }
}
