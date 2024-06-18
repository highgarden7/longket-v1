package com.longketdan.longket.v1.repository.challenge;

import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.support.enums.ApproveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, String> {
    Page<Challenge> findAllByDelYnOrderByCreateDateDesc(String delYn, Pageable pageable);

    Optional<Challenge> findByIdAndDelYn(Long id, String delYn);

    Page<Challenge> findAllByStatus(ApproveStatus status, Pageable pageable);
}
