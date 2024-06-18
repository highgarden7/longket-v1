package com.longketdan.longket.v1.model.entity;

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
@Table(name = "config")
public class Config extends BaseEntity {
    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "foot_trick_filter")
    private String footTrickFilter;

    @Column(name = "hand_trick_filter")
    private String handTrickFilter;

    @Column(name = "dancing_filter")
    private String dancingFilter;
}