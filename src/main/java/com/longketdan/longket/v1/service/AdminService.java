package com.longketdan.longket.v1.service;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.user.UserProgress;
import com.longketdan.longket.v1.model.entity.user.UserRole;
import com.longketdan.longket.v1.repository.user.UserProgressRepository;
import com.longketdan.longket.v1.repository.user.UserRoleRepository;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserProgressRepository userProgressRepository;

    private final UserRoleRepository userRoleRepository;


    public void updateSkillStatus(Long id, Boolean status) {
        UserRole role = userRoleRepository.findByUserId(Util.getUserIdByToken()).orElse(null);

        if (!"ADMIN".equals(role.getRole()))
            throw new CustomException(ErrorCode.ACCESS_DENIED_TOKEN);

        UserProgress userProgress = userProgressRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        userProgress.updateStatus(status);

        userProgressRepository.save(userProgress);
    }
}
