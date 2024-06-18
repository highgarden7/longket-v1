package com.longketdan.longket.v1.application;

import com.longketdan.longket.v1.controller.dto.ChallengeDTO;
import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.model.entity.challenge.ChallengeLine;
import com.longketdan.longket.v1.service.challenge.ChallengeLineService;
import com.longketdan.longket.v1.service.challenge.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeApplicationService {
    private final ChallengeService challengeService;

    private final ChallengeLineService challengeLineService;

    public Page<ChallengeDTO.ChallengeListResponse> getChallengeList(int page, int pageSize) {
        // 챌린지를 최신순, status가 APPROVED인 리스트를 페이징 처리하여 조회
        Page<Challenge> challengeList = challengeService.getChallengeList(page, pageSize);

        return challengeList.map(ChallengeDTO.ChallengeListResponse::of);
    }

    public ChallengeDTO.ChallengeResponse getChallengeDetail(Long id) {
        Challenge challenge = challengeService.getChallengeById(id);
        List<ChallengeLine> challengeLines = challengeLineService.getChallengeLines(id);

        return ChallengeDTO.ChallengeResponse.of(challenge, challengeLines);
    }

    public void createChallenge(ChallengeDTO.CreateChallengeRequest challengeRequest) {
        Challenge challenge = challengeService.createChallenge(challengeRequest.toEntity());

        if (challengeRequest.getChallengeLines() != null) {
            List<ChallengeLine> challengeLines = challengeRequest.getChallengeLines().stream().map(challengeLine -> {
                        ChallengeLine line = challengeLine.toEntity();
                        line.createChallengeLine(challenge.getId());
                        return line;
                    }).toList();

            challengeLineService.createChallengeLines(challengeLines);
        }
    }

    public void updateChallenge(Long id, ChallengeDTO.UpdateChallengeRequest challengeRequest) {
        Challenge challenge = challengeService.updateChallenge(id, challengeRequest.toEntity());

        List<ChallengeLine> challengeLines = challengeRequest.getChallengeLines().stream().map(challengeLine -> {
                    ChallengeLine line = challengeLine.toEntity();
                    line.createChallengeLine(challenge.getId());
                    return line;
                }).toList();

        challengeLineService.updateChallengeLines(id, challengeLines);
    }

    public void closeChallenge(Long id, ChallengeDTO.CloseChallengeRequest closeChallengeRequest) {
        challengeService.closeChallenge(id, closeChallengeRequest.toEntity());
    }

    public void joinChallenge(Long id, ChallengeDTO.CreateChallengerRequest createChallengerRequest) {
        challengeService.joinChallenge(id, createChallengerRequest.toEntity());
    }
}

