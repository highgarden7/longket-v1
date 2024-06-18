package com.longketdan.longket.v1.model.entity.community;

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
@Table(name = "user_like")
public class Like extends BaseEntity {
    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_type")
    private String categoryType;
}