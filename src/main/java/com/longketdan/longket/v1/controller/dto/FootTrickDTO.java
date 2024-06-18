package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.skill.FootTrick;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class FootTrickDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class trickResponse {
        @Schema(description = "풋트릭 unique Id")
        private Long id;

        @Schema(description = "풋트릭 카테고리 unique Id")
        private Long categoryId;

        @Schema(description = "트릭 original 이름 ex) 샤빗")
        private String originalName;

        @Schema(description = "트릭 한글 풀네임 ex) 널리 프론트 샤빗")
        private String korName;

        @Schema(description = "트릭 영어 풀네임 ex) nollie front shove it")
        private String engName;

        @Schema(description = "트릭 별칭... ex) 샤빗 레인보우")
        private String aliasName;

        @Schema(description = "스탠스 ex) LEFT_NOSE | LEFT_TAIL | RIGHT_NOSE | RIGHT_TAIL")
        private String stance;

        @Schema(description = "보드 회전 방향 ex) CW | CCW")
        private String direction;

        @Schema(description = "몸 회전 방향 ex) CW | CCW")
        private String rotate;

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

        @Schema(description = "트릭 설명")
        private String description;

        @Schema(description = "영상 unique Id")
        private Long videoId;

        @Schema(description = "Youtube Url Link")
        private String youtubeLink;

        public static FootTrickDTO.trickResponse of(FootTrick trick) {
            return trickResponse.builder()
                    .id(trick.getId())
                    .categoryId(trick.getFootTrickCategoryId())
                    .originalName(trick.getOriginalName())
                    .korName(trick.getKorName())
                    .engName(trick.getEngName())
                    .aliasName(trick.getAliasName())
                    .stance(trick.getStance())
                    .direction(trick.getDirection())
                    .footPlant(trick.getFootPlant())
                    .flip(trick.getFlip())
                    .difficulty(trick.getDifficulty())
                    .rotate(trick.getRotate())
                    .boardDegree(trick.getBoardDegree())
                    .bodyDegree(trick.getBodyDegree())
                    .description(trick.getDescription())
                    .videoId(trick.getVideoId())
                    .youtubeLink(trick.getYouTubeLink())
                    .build();
        }
    }
}
