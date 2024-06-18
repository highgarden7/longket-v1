package com.longketdan.longket.v1.model.entity.skill;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "hand_trick")
public class HandTrick extends BaseEntity {
    // ex) shove-it
    @Column(name = "original_name")
    private String originalName;

    @Column(name = "hand_trick_category_id")
    private Long handTrickCategoryId;

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

    // 보드 회전 방향 CW | CCW
    @Column(name = "rotate")
    private String rotate;

    // 몸 회전 방향 CW | CCW
    @Column(name = "direction")
    private String direction;

    // 0 | 1 | 2 | 3 ...
    @Column(name = "foot_plant")
    private Long footPlant;

    // kick | heel | pressure | late ...
    @Column(name = "flip")
    private String flip;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "board_degree")
    private Long boardDegree;

    @Column(name = "body_degree")
    private Long bodyDegree;

    @Column(name = "description")
    private String description;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @Column(name = "youtube_link")
    private String youTubeLink;
}
