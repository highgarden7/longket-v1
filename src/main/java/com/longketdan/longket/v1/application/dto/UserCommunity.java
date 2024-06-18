package com.longketdan.longket.v1.application.dto;

import com.longketdan.longket.v1.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCommunity {
    private Long userId;

    private String instaId;

    private String nickName;

    // TODO 뱃지
//    private List<String> badges;

    private String profileImageUrl;

    public static UserCommunity toDto(User user, String profileImageUrl) {
        return UserCommunity.builder()
                .userId(user.getId())
                .instaId(user.getInstaId())
                .nickName(user.getNickName())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
