package com.longketdan.longket.v1.controller.dto;

import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.model.entity.challenge.ChallengeLine;
import com.longketdan.longket.v1.model.entity.challenge.Challenger;
import com.longketdan.longket.v1.support.enums.ApproveStatus;
import com.longketdan.longket.v1.support.enums.ChallengeState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengeDTO {
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChallengeRequest {
        @Schema(description = "챌린지 제목", example = "챌린지 제목")
        private String title;

        @Schema(description = "챌린지 시작일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime startDate;

        @Schema(description = "챌린지 종료일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime endDate;

        @Schema(description = "전화번호", example = "010-1234-5678")
        private String tel;

        @Schema(description = "챌린지 라인", example = "챌린지 라인")
        private List<ChallengeLineDTO.createChallengeLineRequest> challengeLines;

        @Schema(description = "챌린지 설명", example = "챌린지 설명")
        private String description;

        @Schema(description = "챌린지 URL", example = "https://www.naver.com")
        private String challengeUrl;

        public Challenge toEntity() {
            return Challenge.builder()
                    .title(this.title)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .description(this.description)
                    .challengeUrl(this.challengeUrl)
                    .status(ApproveStatus.WAIT)
                    .delYn("N")
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateChallengeRequest {
        @Schema(description = "챌린지 제목", example = "챌린지 제목")
        private String title;

        @Schema(description = "챌린지 시작일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime startDate;

        @Schema(description = "챌린지 종료일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime endDate;

        @Schema(description = "챌린지 라인", example = "챌린지 라인")
        private List<ChallengeLineDTO.createChallengeLineRequest> challengeLines;

        @Schema(description = "챌린지 설명", example = "챌린지 설명")
        private String description;

        @Schema(description = "챌린지 URL", example = "https://www.naver.com")
        private String challengeUrl;

        public Challenge toEntity() {
            return Challenge.builder()
                    .title(this.title)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .description(this.description)
                    .challengeUrl(this.challengeUrl)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CloseChallengeRequest {
        @Schema(description = "챌린지 종료 제목", example = "챌린지 종료 제목")
        private String closeTitle;

        @Schema(description = "챌린지 종료 설명", example = "챌린지 종료 설명")
        private String closeDescription;

        public Challenge toEntity() {
            return Challenge.builder()
                    .closeTitle(this.closeTitle)
                    .closeDescription(this.closeDescription)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeListResponse {
        @Schema(description = "챌린지 ID", example = "1")
        private Long id;

        @Schema(description = "챌린지 제목", example = "챌린지 제목")
        private String title;

        @Schema(description = "챌린지 시작일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime startDate;

        @Schema(description = "챌린지 종료일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime endDate;

        @Schema(description = "전화번호", example = "010-1234-5678")
        private String tel;

        @Schema(description = "챌린지 설명", example = "챌린지 설명")
        private String description;

        @Schema(description = "챌린지 URL", example = "https://www.naver.com")
        private String challengeUrl;

        @Schema(description = "챌린지 종료 타이틀", example = "챌린지 종료 타이틀")
        private String closeTitle;

        @Schema(description = "챌린지 종료 설명", example = "챌린지 종료 설명")
        private String closeDescription;

        @Schema(description = "챌린지 상태", example = "챌린지 상태")
        private ChallengeState state;

        public static ChallengeListResponse of(Challenge challenge) {
            LocalDateTime now = LocalDateTime.now();
            return ChallengeListResponse.builder()
                    .id(challenge.getId())
                    .title(challenge.getTitle())
                    .startDate(challenge.getStartDate())
                    .endDate(challenge.getEndDate())
                    .description(challenge.getDescription())
                    .challengeUrl(challenge.getChallengeUrl())
                    .closeTitle(challenge.getCloseTitle())
                    .closeDescription(challenge.getCloseDescription())
                    .state(challenge.getEndDate().isBefore(now) ? ChallengeState.ENDED : ChallengeState.OPEN)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeResponse {
        @Schema(description = "챌린지 ID", example = "1")
        private Long id;

        @Schema(description = "챌린지 제목", example = "챌린지 제목")
        private String title;

        @Schema(description = "챌린지 시작일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime startDate;

        @Schema(description = "챌린지 종료일", example = "2021.01.01 00:00:00")
        @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
        private LocalDateTime endDate;

        @Schema(description = "전화번호", example = "010-1234-5678")
        private String tel;

        @Schema(description = "챌린지 라인", example = "챌린지 라인")
        private List<ChallengeLineDTO.ChallengeLineResponse> challengeLines;

        @Schema(description = "챌린지 설명", example = "챌린지 설명")
        private String description;

        @Schema(description = "챌린지 URL", example = "https://www.naver.com")
        private String challengeUrl;

        @Schema(description = "챌린지 종료 타이틀", example = "챌린지 종료 타이틀")
        private String closeTitle;

        @Schema(description = "챌린지 종료 설명", example = "챌린지 종료 설명")
        private String closeDescription;

        @Schema(description = "챌린지 상태", example = "챌린지 상태")
        private ChallengeState state;

        public static ChallengeResponse of(Challenge challenge, List<ChallengeLine> challengeLine) {
            LocalDateTime now = LocalDateTime.now();

            return ChallengeResponse.builder()
                    .id(challenge.getId())
                    .title(challenge.getTitle())
                    .startDate(challenge.getStartDate())
                    .endDate(challenge.getEndDate())
                    .description(challenge.getDescription())
                    .challengeLines(challengeLine != null ? challengeLine.stream().map(ChallengeLineDTO.ChallengeLineResponse::of).toList() : null)
                    .challengeUrl(challenge.getChallengeUrl())
                    .closeTitle(challenge.getCloseTitle())
                    .closeDescription(challenge.getCloseDescription())
                    .state(challenge.getEndDate().isBefore(now) ? ChallengeState.ENDED : ChallengeState.OPEN)
                    .build();
        }
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChallengerRequest {
        @Schema(description = "유저 ID", example = "1")
        private Long userId;

        @Schema(description = "비디오 Link", example = "www.naver.com")
        private String videoLink;

        @Schema(description = "순위", example = "1")
        private String ranking;

        public Challenger toEntity() {
            return Challenger.builder()
                    .userId(this.userId)
                    .videoLink(this.videoLink)
                    .ranking(this.ranking)
                    .build();
        }
    }
}
