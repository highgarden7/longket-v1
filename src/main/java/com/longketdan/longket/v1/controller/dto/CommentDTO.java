package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.community.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class CommentDTO {

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCommentRequest {
        @Schema(description = "타겟 unique Id")
        private Long targetId;

        @Schema(description = "부모 댓글 unique Id")
        private Long parentCommentId;

        @Schema(description = "카테고리 타입 ex) skill | challenge ...")
        private String categoryType;

        @Schema(description = "댓글 내용")
        private String content;

        public static Comment toEntity(CreateCommentRequest createCommentRequest) {
            return Comment.builder()
                    .targetId(createCommentRequest.getTargetId())
                    .parentCommentId(createCommentRequest.getParentCommentId())
                    .categoryType(createCommentRequest.getCategoryType())
                    .content(createCommentRequest.getContent())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCommentRequest {
        @Schema(description = "댓글 내용")
        private String content;

        public static Comment toEntity(UpdateCommentRequest updateCommentRequest) {
            return Comment.builder()
                    .content(updateCommentRequest.getContent())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse {
        @Schema(description = "코멘트 unique Id")
        private Long commentId;

        @Schema(description = "유저 unique Id")
        private Long userId;

        @Schema(description = "댓글 내용")
        private String content;

        @Schema(description = "부모 댓글 unique Id")
        private Long parentCommentId;

        @Schema(description = "하위 댓글 존재 여부")
        private Boolean hasChild;

        public static CommentResponse of(Comment comment, Boolean hasChild) {
            return CommentResponse.builder()
                    .commentId(comment.getId())
                    .userId(comment.getUserId())
                    .content(comment.getContent())
                    .parentCommentId(comment.getParentCommentId())
                    .hasChild(hasChild)
                    .build();
        }
    }
}
