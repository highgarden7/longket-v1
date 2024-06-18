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
@Table(name = "basic_skill")
public class BasicSkill extends BaseEntity {
    // ex) shove-it
    @Column(name = "original_name")
    private String originalName;

    @Column(name = "kor_name")
    private String korName;

    @Column(name = "eng_name")
    private String engName;

    // big-spin
    @Column(name = "alias_name")
    private String aliasName;

    @Column(name = "description")
    private String description;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @Column(name = "youtube_link")
    private String youTubeLink;
}