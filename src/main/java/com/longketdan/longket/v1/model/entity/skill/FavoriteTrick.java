package com.longketdan.longket.v1.model.entity.skill;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "favorite_trick")
@Entity
public class FavoriteTrick extends BaseEntity {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "trick_id")
    private Long trickId;

    @Column(name = "category_type")
    private String categoryType;
}
