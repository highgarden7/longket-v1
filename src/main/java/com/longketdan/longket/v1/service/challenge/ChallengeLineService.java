package com.longketdan.longket.v1.service.challenge;

import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.model.entity.challenge.ChallengeLine;
import com.longketdan.longket.v1.repository.challenge.ChallengeLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeLineService {
    private final ChallengeLineRepository challengeLineRepository;

    public List<ChallengeLine> getChallengeLinesByChallengeId(Long id) {
        return challengeLineRepository.findByChallengeIdOrderByOrderNum(id);
    }

    public void createChallengeLine(Long challengeId, List<ChallengeLine> challengeLineList) {
        for (ChallengeLine challengeLine : challengeLineList) {
            challengeLine.createChallengeLine(challengeId);
            challengeLineRepository.save(challengeLine);
        }
    }

    public void updateChallengeLines(Long challengeId, List<ChallengeLine> challengeLineList) {
        // delete all challenge line
        challengeLineRepository.deleteByChallengeId(challengeId);
        // create challenge line
        createChallengeLine(challengeId, challengeLineList);
    }

    public void createChallengeLines(List<ChallengeLine> challengeLines) {
        challengeLineRepository.saveAll(challengeLines);
    }

    public List<ChallengeLine> getChallengeLines(Long challengeId) {
        return challengeLineRepository.findByChallengeIdOrderByOrderNum(challengeId);
    }
}
