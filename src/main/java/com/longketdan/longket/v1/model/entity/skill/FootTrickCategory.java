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
@Table(name = "foot_trick_category")
public class FootTrickCategory extends BaseEntity {
    // ex) 샤빗
    @Column(name = "original_kor_name")
    private String originalKorName;

    // ex) shove-it
    @Column(name = "original_eng_name")
    private String originalEngName;

    // big-spin
    @Column(name = "alias_name")
    private String aliasName;

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

    @Column(name = "thumb_nail_id")
    private Long thumbNailId;

    @Column(name = "is_enable")
    private Boolean isEnable;
}