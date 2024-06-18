package com.longketdan.longket.v1.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class ConfigDTO {
    @Schema(description = "App version")
    private String version;

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterListDTO {
        @Schema(description = "풋트릭 필터")
        private List<String> footTrickFilter;

        @Schema(description = "핸드트릭 필터")
        private List<String> handTrickFilter;

        @Schema(description = "댄싱 필터")
        private List<String> dancingFilter;

        public static ConfigDTO.FilterListDTO of(List<String> footTrickFilter, List<String> handTrickFilter, List<String> dancingFilter) {
            return FilterListDTO.builder()
                    .footTrickFilter(footTrickFilter)
                    .handTrickFilter(handTrickFilter)
                    .dancingFilter(dancingFilter)
                    .build();
        }
    }
}
