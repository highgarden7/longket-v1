package com.longketdan.longket.v1.repository.challenge;

import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.model.entity.challenge.ChallengeLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeLineRepository extends JpaRepository<ChallengeLine, String> {
    void deleteByChallengeId(Long challengeId);

    List<ChallengeLine> findByChallengeIdOrderByOrderNum(Long id);
}
