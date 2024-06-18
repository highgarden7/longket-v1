package com.longketdan.longket.v1.model.entity.challenge;

import com.longketdan.longket.v1.model.entity.BaseEntity;
import com.longketdan.longket.v1.support.enums.ApproveStatus;
import com.longketdan.longket.v1.support.enums.HostType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challenge")
public class Challenge extends BaseEntity {
    @Column(name = "title")
    private String title;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "host_type")
    private HostType hostType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "close_title")
    private String closeTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "close_description")
    private String closeDescription;

    @Column(name = "challenge_url")
    private String challengeUrl;

    @Column(name = "del_yn")
    private String delYn;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private ApproveStatus status;

    public void createChallenge(Long userId) {
        this.userId = userId;
        this.status = ApproveStatus.WAIT;
    }

    public void updateChallenge(Challenge challenge) {
        if (challenge.getTitle() != null) this.title = challenge.getTitle();
        if (challenge.getStartDate() != null) this.startDate = challenge.getStartDate();
        if (challenge.getEndDate() != null) this.endDate = challenge.getEndDate();
        if (challenge.getDescription() != null) this.description = challenge.getDescription();
        if (challenge.getChallengeUrl() != null) this.challengeUrl = challenge.getChallengeUrl();
    }

    public void closeChallenge(Challenge challenge) {
        this.closeTitle = challenge.getCloseTitle();
        this.closeDescription = challenge.getCloseDescription();
    }


    public void deleteChallenge() {
        this.delYn = "Y";
    }

    public void changeStatus(boolean isApprove) {
        if (this.status == ApproveStatus.WAIT) {
            if (isApprove) {
                this.status = ApproveStatus.APPROVED;
            } else {
                this.status = ApproveStatus.REJECTED;
            }
        }
    }


}


