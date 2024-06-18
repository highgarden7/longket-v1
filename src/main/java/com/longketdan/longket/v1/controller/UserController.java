package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.UserApplicationService;
import com.longketdan.longket.v1.controller.dto.UserDTO;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    private final UserApplicationService userApplicationService;

    @PostMapping
    @Operation(summary = "회원가입", description = "유저 회원가입 요청")
    public ResponseEntity<Object> InsertUser(@RequestBody UserDTO.MyInfoRequest user) {
        return CommonResponse.dataResponseEntity(userService.joinUser(user.toEntity()));
    }

    @GetMapping
    @Operation(summary = "유저 검색", description = "유저 검색")
    public ResponseEntity<Object> getAllUserList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestParam(value = "keyword", required = false) String keyword) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(userService.getAllUserList(page, pageSize, keyword).map(UserDTO.UserResponse::of)));

    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return CommonResponse.dataResponseEntity(UserDTO.UserResponse.of(userService.getUserById(id)));
    }

    @GetMapping("/myInfo")
    @Operation(summary = "내 정보 조회", description = "자신의 계정 정보 요청")
    public ResponseEntity<Object> getUserInfo() {
        return CommonResponse.dataResponseEntity(userApplicationService.getMyInfo());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "유저 정보 수정", description = "유저의 정보 수정")
    public User updateUserById(@PathVariable Long id, @RequestBody UserDTO.MyInfoRequest user) {
        return userService.updateUser(id, user.toEntity());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "유저 삭제", description = "유저 삭제 요청 (추후 로직추가 필요)")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/follow/isFollowing/{followId}")
    @Operation(summary = "팔로우 여부 확인", description = "팔로우 여부 확인")
    public ResponseEntity<Object> isFollowing(@PathVariable(value = "followId") Long followId) {
        return CommonResponse.dataResponseEntity(userService.isFollowing(followId));
    }

    @PostMapping("/follow")
    @Operation(summary = "팔로우", description = "유저 팔로우")
    public ResponseEntity<Object> followUser(@RequestParam(value = "followId") Long followId) {
        userService.followUser(followId);
        return CommonResponse.successResponseEntity();
    }

    @DeleteMapping("/unFollow")
    @Operation(summary = "언팔로우", description = "유저 언팔로우")
    public ResponseEntity<Object> unFollowUser(@RequestParam(value = "followId") Long followId) {
        userService.unFollowUser(followId);
        return CommonResponse.successResponseEntity();
    }

    @GetMapping("/following/list/{userId}")
    @Operation(summary = "팔로잉 리스트 조회", description = "팔로잉 리스트 조회")
    public ResponseEntity<Object> getFollowList(@PathVariable(value = "userId") Long userId,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                @RequestParam(value = "keyword", required = false) String keyword) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(userService.getFollowingList(userId, page, pageSize, keyword)));
    }

    @GetMapping("/follower/list/{userId}")
    @Operation(summary = "팔로워 리스트 조회", description = "팔로워 리스트 조회")
    public ResponseEntity<Object> getFollowerList(@PathVariable(value = "userId") Long userId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                  @RequestParam(value = "keyword", required = false) String keyword) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(userService.getFollowerList(userId, page, pageSize, keyword)));
    }

    @GetMapping("/count-all")
    @Operation(summary = "전체 유저 수 조회", description = "전체 유저 수 조회")
    public ResponseEntity<Object> countAllUser() {
        return CommonResponse.dataResponseEntity(userService.countAllUser());
    }
}
