package com.longketdan.longket.v1.model.entity.user;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "user_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress extends BaseEntity {
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "description")
    private String description;

    @Column(name = "open_yn")
    private String openYn;

    @Column(name = "comment_yn")
    private String commentYn;

    @Column(name = "status")
    private String status;

    public void createUserProgress(Long userId) {
        this.userId = userId;
        this.status = "WAIT";
    }

    public void updateStatus(Boolean status) {
        if (!"WAIT".equals(this.status)) {
            return;
        }

        if (status) {
            this.status = "APPROVED";
        } else {
            this.status = "REJECTED";
        }
    }

    public void rejectStatus() {
        this.status = "REJECTED";
    }
}
