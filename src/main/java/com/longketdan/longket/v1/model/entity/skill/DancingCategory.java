package com.longketdan.longket.v1.model.entity.skill;

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
@Table(name = "dancing_category")
public class DancingCategory extends BaseEntity {
    @Column(name = "original_kor_name")
    private String originalKorName;

    @Column(name = "original_eng_name")
    private String originalEngName;

    @Column(name = "alias_name")
    private String aliasName;

    @Column(name = "driving_direction")
    private String drivingDirection;

    @Column(name = "foot_position")
    private String footPosition;

    @Column(name = "carving")
    private String carving;

    @Column(name = "body_degree")
    private Long bodyDegree;

    @Column(name = "steps")
    private Long steps;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "thumb_nail_id")
    private Long thumbNailId;

    @Column(name = "is_enable")
    private Boolean isEnable;
}