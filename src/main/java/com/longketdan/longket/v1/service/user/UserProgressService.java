package com.longketdan.longket.v1.service.user;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.UserProgressDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.user.UserProgress;
import com.longketdan.longket.v1.repository.skill.DancingRepository;
import com.longketdan.longket.v1.repository.skill.FootTrickRepository;
import com.longketdan.longket.v1.repository.skill.HandTrickRepository;
import com.longketdan.longket.v1.repository.user.UserProgressRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;

    private final FootTrickRepository footTrickRepository;

    private final HandTrickRepository handTrickRepository;

    private final DancingRepository dancingRepository;

    private final UserRepository userRepository;

    public UserProgress createUserProgress(UserProgress userProgress) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        userProgress.createUserProgress(user.getId());
        return userProgressRepository.save(userProgress);
    }

    public int getUserFootTrickCount(Long userId) {
        return userProgressRepository.countByUserIdAndCategoryTypeAndStatus(userId, "foot_trick", "APPROVED");
    }

    public int getUserProgressCountWithCategory(Long categoryId, String categoryType) {
        return userProgressRepository.countByUserIdAndCategoryIdAndCategoryTypeAndStatus(Util.getUserIdByToken(), categoryId, categoryType, "APPROVED");
    }

    public int getUserFootTrickCountWithCategory(Long userId, Long categoryId) {
        return userProgressRepository.countByUserIdAndCategoryIdAndCategoryType(userId, categoryId, "foot_trick");
    }

    public int getUserHandTrickCount(Long userId) {
        return userProgressRepository.countByUserIdAndCategoryTypeAndStatus(userId, "hand_trick", "APPROVED");
    }

    public int getUserHandTrickCountWithCategory(Long userId, Long categoryId) {
        return userProgressRepository.countByUserIdAndCategoryIdAndCategoryType(userId, categoryId, "hand_trick");
    }

    public int getUserDancingCount(Long userId) {
        return userProgressRepository.countByUserIdAndCategoryTypeAndStatus(userId, "dancing", "APPROVED");
    }

    public int getUserDancingCountWithCategory(Long userId, Long categoryId) {
        return userProgressRepository.countByUserIdAndCategoryIdAndCategoryType(userId, categoryId, "dancing");
    }


    public UserProgressDTO.UserSkillProgressResponse getUserProgress(Long userId) {
        int footTrickCount = getUserFootTrickCount(userId);
        int handTrickCount = getUserHandTrickCount(userId);
        int dancingCount = getUserDancingCount(userId);

        return UserProgressDTO.UserSkillProgressResponse.of(footTrickCount, handTrickCount, dancingCount);
    }

    public UserProgressDTO.SkillProgressResponse getAllProgress() {
        int totalFootTrickCount = footTrickRepository.countByIsEnable(true);
        int totalHandTrickCount = handTrickRepository.countByIsEnable(true);
        int totalDancingCount = dancingRepository.countByIsEnable(true);

        return UserProgressDTO.SkillProgressResponse.builder()
                .totalFootTrickCount(totalFootTrickCount)
                .totalHandTrickCount(totalHandTrickCount)
                .totalDancingCount(totalDancingCount)
                .build();
    }

    public List<UserProgress> getUserProgressDetail(Long categoryId, Long skillId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return userProgressRepository.findByUserIdAndCategoryIdAndSkillIdAndCategoryTypeOrderByIdDesc(user.getId(), categoryId, skillId, categoryType);
    }

    public Page<UserProgress> getProgressDetailList(int page, int pageSize, Long categoryId, Long skillId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return userProgressRepository.findByCategoryIdAndSkillIdAndStatusAndOpenYnAndCategoryTypeAndUserIdNotOrderByUpdateDateDesc(categoryId, skillId, "APPROVED", "Y", categoryType, user.getId(), pageable);
    }

    public Page<UserProgress> getAllUserProgress(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // 그렇지 않은 경우 openYn이 'Y'인 데이터만 검색
        return userProgressRepository.findByOpenYnAndStatusOrderByUpdateDateDesc("Y", "APPROVED", pageable);
    }

    public Page<UserProgress> getUserProgressByUserId(int page, int pageSize, Long userId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // 그렇지 않은 경우 openYn이 'Y'인 데이터만 검색
        return userProgressRepository.findByUserIdAndOpenYnOrderByCreateDateDesc(userId, "Y", pageable);
    }

    public Page<UserProgress> getUserSkillProgressByCategoryType(int page, int pageSize, Long userId, String categoryType) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // 로그인된 사용자의 userId와 파라미터로 받은 userId가 동일한 경우
        if (user.getId().equals(userId)) {
            return userProgressRepository.findByUserIdAndCategoryType(userId, categoryType, pageable);
        } else {
            // 그렇지 않은 경우 openYn이 'Y'인 데이터만 검색
            return userProgressRepository.findByUserIdAndCategoryTypeAndOpenYn(userId, categoryType, "Y", pageable);
        }
    }

    public Page<UserProgress> getAllUserSkillProgressBySkillIdAndCategoryType(int page, int pageSize, Long skillId, String categoryType) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // 그렇지 않은 경우 openYn이 'Y'인 데이터만 검색
        return userProgressRepository.findByCategoryTypeAndSkillIdAndOpenYn(categoryType, skillId, "Y", pageable);
    }

    public Page<UserProgress> getAllWaitedUserProgressList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<UserProgress> list = userProgressRepository.findByStatusOrderByCreateDateAsc("WAIT", pageable);
        return list;
    }

    public void rejectUserProgress(Long userProgressId) {
        UserProgress userProgress = userProgressRepository.findById(userProgressId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (!Util.getUserIdByToken().equals(userProgress.getUserId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED_TOKEN);
        }

        userProgress.rejectStatus();

        userProgressRepository.save(userProgress);
    }
}
