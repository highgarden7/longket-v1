package com.longketdan.longket.v1.model.entity.challenge;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challenge_line")
public class ChallengeLine extends BaseEntity {
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "order_num")
    private int orderNum;

    public void createChallengeLine(Long challengeId) {
        this.challengeId = challengeId;
    }
}
