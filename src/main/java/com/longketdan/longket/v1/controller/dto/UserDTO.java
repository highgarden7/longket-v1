package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.user.UserFollow;
import com.longketdan.longket.v1.model.entity.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OauthResponse {
        private String providerId;

        private String provider;

        private String email;
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        @Schema(description = "유저 고유 ID")
        private Long id;

        @Schema(description = "유저 email")
        private String email;

        @Schema(description = "유저 닉네임")
        private String nickName;

        @Schema(description = "Instagram ID")
        private String instaId;

        @Schema(description = "생년월일")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birth;

        private String location;

        @Schema(description = "롱보드 경험 유무")
        private Boolean isExperienced;

        @Schema(description = "보드 스탠스")
        @Pattern(regexp = "Goofy|Regular")
        private String stance;

        @Schema(description = "프로필 이미지 Id")
        private Long profileImgId;

        public static UserResponse of(User user) {
            return UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickName(user.getNickName())
                    .instaId(user.getInstaId())
                    .stance(user.getStance())
                    .isExperienced(user.getIsExperienced())
                    .profileImgId(user.getProfileImgId())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyInfoRequest {
        @Schema(description = "유저 닉네임")
        private String nickName;

        @Schema(description = "Instagram ID")
        private String instaId;

        @Schema(description = "생년월일")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birth;

        @Schema(description = "롱보드 경험 유무")
        private Boolean isExperienced;

        private String location;

        @Schema(description = "보드 스탠스")
        @Pattern(regexp = "Goofy|Regular")
        private String stance;

        @Schema(description = "refreshToken")
        private String refreshToken;

        @Schema(description = "프로필 이미지 Id")
        private Long profileImgId;

        public User toEntity() {
            User user = new User();
            user.setNickName(this.nickName);
            user.setInstaId(this.instaId);
            user.setBirth(this.birth);
            user.setIsExperienced(this.isExperienced);
            user.setStance(this.stance);
            user.setRefreshToken(this.refreshToken);
            user.setProfileImgId(this.profileImgId);

            return user;
        }

    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyInfoResponse {
        @Schema(description = "유저 고유 ID")
        private Long id;

        @Schema(description = "유저 email")
        private String email;

        @Schema(description = "유저 닉네임")
        private String nickName;

        @Schema(description = "Instagram ID")
        private String instaId;

        @Schema(description = "생년월일")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birth;

        @Schema(description = "롱보드 경험 유무")
        private Boolean isExperienced;

        private String location;

        @Schema(description = "처음 롱보드 시작일")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate firstStartDate;

        @Schema(description = "보드 스탠스")
        private String stance;

        @Schema(description = "프로필 이미지 Id")
        private Long profileImgId;

        @Schema(description = "유저 권한")
        private String role;

        public static MyInfoResponse of(User user, UserRole role) {
            return MyInfoResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickName(user.getNickName())
                    .instaId(user.getInstaId())
                    .birth(user.getBirth())
                    .isExperienced(user.getIsExperienced())
                    .stance(user.getStance())
                    .profileImgId(user.getProfileImgId())
                    .role(role != null ? role.getRole() : null)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowingResponse {
        private Long id;

        private Boolean amIFollowing;

        private Long userId;

        public static FollowingResponse of(UserFollow userFollow, Boolean amIFollowing) {
            return FollowingResponse.builder()
                    .id(userFollow.getId())
                    .amIFollowing(amIFollowing)
                    .userId(userFollow.getFollow().getId())
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowerResponse {
        private Long id;

        private Boolean amIFollowing;

        private Long userId;

        public static FollowerResponse of(UserFollow userFollow, Boolean amIFollowing) {
            return FollowerResponse.builder()
                    .id(userFollow.getId())
                    .amIFollowing(amIFollowing)
                    .userId(userFollow.getUser().getId())
                    .build();
        }
    }
}
