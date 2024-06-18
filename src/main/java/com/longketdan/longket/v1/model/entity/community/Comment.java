package com.longketdan.longket.v1.model.entity.community;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "content")
    private String content;

    public void createComment(Long userId) {
        this.userId = userId;
    }
}