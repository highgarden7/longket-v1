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
@Table(name = "challenger")
public class Challenger extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "ranking")
    private String ranking;

    public void create(Long challengeId) {
        this.challengeId = challengeId;
    }

    public void update(String ranking) {
        this.ranking = ranking;
    }
}
