package com.longketdan.longket.v1.service.user;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.UserDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.model.entity.user.UserFollow;
import com.longketdan.longket.v1.repository.user.UserFollowRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.util.Util;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserFollowRepository userFollowRepository;

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public User joinUser(User user) {
        User originalUser = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (userRepository.findByNickName(user.getNickName()).orElse(null) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        originalUser.setNickName(user.getNickName());
        originalUser.setIsExperienced(user.getIsExperienced());
        originalUser.setBirth(user.getBirth());
        originalUser.setInstaId(user.getInstaId());
        originalUser.setStance(user.getStance());

        return userRepository.save(originalUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public User getUserByEmailAndDelYn(String email, String delYn) {
        return userRepository.findByEmailAndDelYn(email, delYn).orElse(null);
    }

    public User updateUser(Long id, User user) {
        User tempUser = userRepository.findById(id).orElse(null);
        if (tempUser != null) {
            tempUser.setInstaId(user.getInstaId());
            tempUser.setNickName(user.getNickName());
            tempUser.setRefreshToken(user.getRefreshToken());
            tempUser.setProfileImgId(user.getProfileImgId());
            tempUser.setStance(user.getStance());
            return userRepository.save(tempUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDelYn("Y");
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean isFollowing(Long followId) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        return userFollowRepository.existsByUserIdAndFollowId(user.getId(), followId);
    }

    public void followUser(Long followId) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        User following = userRepository.findById(followId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        userFollowRepository.findByUserIdAndFollowId(user.getId(), following.getId()).ifPresentOrElse(
                userFollowRepository::delete,
                () -> userFollowRepository.save(UserFollow.builder()
                        .user(user)
                        .follow(following)
                        .build())
        );
    }

    public void unFollowUser(Long followId) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        User following = userRepository.findById(followId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        userFollowRepository.delete(userFollowRepository.findByUserIdAndFollowId(user.getId(), following.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)));
    }

    public Page<UserDTO.FollowingResponse> getFollowingList(Long userId, int page, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // keyword를 포함하여 검색기능 구현
        Specification<UserFollow> spec = Specification.where(queryIsFollowing(userId)).and(queryFollowByNickNameContains(keyword));

        Page<UserFollow> pageList = userFollowRepository.findAll(spec, pageable);

        return pageList.map(userFollow -> UserDTO.FollowingResponse.of(userFollow, isFollowing(userFollow.getFollow().getId())));
    }

    public Page<UserDTO.FollowerResponse> getFollowerList(Long followId, int page, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // keyword를 포함하여 검색기능 구현
        Specification<UserFollow> spec = Specification.where(queryIsFollower(followId)).and(queryFollowNickNameContains(keyword));

        Page<UserFollow> pageList = userFollowRepository.findAll(spec, pageable);

        return pageList.map(userFollow -> UserDTO.FollowerResponse.of(userFollow, isFollowing(userFollow.getUser().getId())));
    }

    // 팔로우 리스트 닉네임 검색
    private Specification<UserFollow> queryFollowNickNameContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            var userJoin = root.join("user");
            var nicknamePredicate = criteriaBuilder.like(userJoin.get("nickName"), "%" + keyword + "%");
            var emailPredicate = criteriaBuilder.like(userJoin.get("email"), "%" + keyword + "%");
            return criteriaBuilder.or(nicknamePredicate, emailPredicate);
        };
    }

    // 팔로잉 리스트 닉네임 검색
    private Specification<UserFollow> queryFollowByNickNameContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            var userJoin = root.join("follow");
            var nicknamePredicate = criteriaBuilder.like(userJoin.get("nickName"), "%" + keyword + "%");
            var emailPredicate = criteriaBuilder.like(userJoin.get("email"), "%" + keyword + "%");
            return criteriaBuilder.or(nicknamePredicate, emailPredicate);
        };
    }

    private Specification<UserFollow> queryIsFollowing(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    private Specification<UserFollow> queryIsFollower(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("follow").get("id"), userId);
    }

    public int countAllUser() {
        return (int) userRepository.count();
    }

    public Page<User> getAllUserList(int page, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                Predicate nickNamePredicate = criteriaBuilder.like(root.get("nickName"), "%" + keyword + "%");
                predicates.add(criteriaBuilder.or(emailPredicate, nickNamePredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec, pageable);
    }
}
