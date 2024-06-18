package com.longketdan.longket.v1.repository.challenge;

import com.longketdan.longket.v1.model.entity.challenge.Challenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengerRepository extends JpaRepository<Challenger, Long> {

    Optional<Challenger> findByChallengeIdAndUserId(Long userId, Long challengeId);
}
