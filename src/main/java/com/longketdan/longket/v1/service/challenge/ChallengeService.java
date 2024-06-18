package com.longketdan.longket.v1.service.challenge;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.challenge.Challenge;
import com.longketdan.longket.v1.model.entity.challenge.Challenger;
import com.longketdan.longket.v1.model.entity.user.UserRole;
import com.longketdan.longket.v1.repository.challenge.ChallengeRepository;
import com.longketdan.longket.v1.repository.challenge.ChallengerRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.repository.user.UserRoleRepository;
import com.longketdan.longket.v1.support.enums.ApproveStatus;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengerRepository challengerRepository;

    // 챌린지를 최신순, status가 APPROVED인 리스트를 페이징 처리하여 조회
    public Page<Challenge> getChallengeList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createDate").descending());

        return challengeRepository.findAllByStatus(ApproveStatus.APPROVED, pageable);
    }

    public Challenge getChallengeById(Long id) {
        return challengeRepository.findByIdAndDelYn(id, "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public Challenge createChallenge(Challenge challenge) {
        challenge.createChallenge(Util.getUserIdByToken());
        return challengeRepository.save(challenge);
    }

    public Challenge updateChallenge(Long id, Challenge challenge) {
        Challenge originChallenge = challengeRepository.findByIdAndDelYn(id, "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        originChallenge.updateChallenge(challenge);
        return challengeRepository.save(originChallenge);
    }

    public void deleteChallenge(String id) {
        UserRole role = userRoleRepository.findByUserId(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (!"ADMIN".equals(role.getRole())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED_TOKEN);
        }

        Challenge challenge = challengeRepository.findByIdAndDelYn(Long.parseLong(id), "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        challenge.deleteChallenge();
        challengeRepository.save(challenge);
    }

    public void closeChallenge(Long id, Challenge challenge) {
        Challenge originChallenge = challengeRepository.findByIdAndDelYn(id, "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        originChallenge.closeChallenge(challenge);
        challengeRepository.save(originChallenge);
    }

    public void joinChallenge(Long id, Challenger challenger) {
        Challenge challenge = challengeRepository.findByIdAndDelYn(id, "N").orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        challenger.create(challenge.getId());
        challengerRepository.save(challenger);
    }

    public void outChallenge(Long id, Long userId) {
        Challenger challenger = challengerRepository.findByChallengeIdAndUserId(id, userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        challengerRepository.delete(challenger);
    }
}
