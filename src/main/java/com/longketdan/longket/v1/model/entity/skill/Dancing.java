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
@Table(name = "dancing")
public class Dancing extends BaseEntity {
    // ex) shove-it
    @Column(name = "original_name")
    private String originalName;

    @Column(name = "dancing_category_id")
    private Long dancingCategoryId;

    @Column(name = "kor_name")
    private String korName;

    @Column(name = "eng_name")
    private String engName;

    // big-spin
    @Column(name = "alias_name")
    private String aliasName;

    // LEFT_NOSE | LEFT_TAIL | RIGHT_NOSE | RIGHT_TAIL
    @Column(name = "stance")
    private String stance;

    // 몸 회전 방향 CW | CCW
    @Column(name = "foot_position")
    private String footPosition;

    // 0 | 1 | 2 | 3 ...
    @Column(name = "carving")
    private String carving;

    // kick | heel | pressure | late ...
    @Column(name = "board_side")
    private String boardSide;

    @Column(name = "body_degree")
    private Long bodyDegree;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "description")
    private String description;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @Column(name = "youtube_link")
    private String youTubeLink;
}