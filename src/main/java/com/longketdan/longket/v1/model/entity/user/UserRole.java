package com.longketdan.longket.v1.model.entity.user;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {

    @Column(name = "role")
    private String role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
