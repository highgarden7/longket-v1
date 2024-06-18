package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public class ResourceDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class resourceResponse {
        @Schema(description = "리소스 unique Id")
        private Long id;

        @Schema(description = "생성일")
        private LocalDateTime createDate;

        @Schema(description = "수정일")
        private LocalDateTime updateDate;

        @Schema(description = "리소스 명")
        private String name;

        @Schema(description = "리소스 url")
        private String rscUrl;

        @Schema(description = "리소스 크기(byte)")
        private Long length;

        public static ResourceDTO.resourceResponse of(Resource resource) {
            return ResourceDTO.resourceResponse.builder()
                    .id(resource.getId())
                    .createDate(resource.getCreateDate())
                    .updateDate(resource.getUpdateDate())
                    .name(resource.getName())
                    .rscUrl(resource.getRscUrl())
                    .length(resource.getLength())
                    .build();
        }
    }
}
