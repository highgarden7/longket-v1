package com.longketdan.longket.v1.model.entity.skill;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "trick_like")
@NoArgsConstructor
@AllArgsConstructor
public class TrickLike extends BaseEntity {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "trick_id")
    private Long trickId;
}
