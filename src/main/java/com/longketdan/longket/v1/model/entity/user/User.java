package com.longketdan.longket.v1.model.entity.user;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    // google | kakao
    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email")
    private String email;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "insta_id")
    private String instaId;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "is_experienced")
    private Boolean isExperienced;

    @Pattern(regexp = "Goofy|Regular")
    @Column(name = "stance")
    private String stance;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "profile_img_id")
    private Long profileImgId;

    @Column(name = "del_yn")
    private String delYn;
}
