package com.longketdan.longket.v1.application;

import com.longketdan.longket.config.jwt.JwtBody;
import com.longketdan.longket.v1.controller.dto.UserDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.user.UserRole;
import com.longketdan.longket.v1.repository.user.UserRoleRepository;
import com.longketdan.longket.v1.service.user.UserService;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserApplicationService {
    private final UserService userService;

    private final UserRoleRepository userRoleRepository;

    public UserDTO.MyInfoResponse getMyInfo() {
        User user = userService.getUserById(Util.getUserIdByToken());

        UserRole role = userRoleRepository.findByUserId(user.getId()).orElse(null);

        return UserDTO.MyInfoResponse.of(user, role);
    }
}

