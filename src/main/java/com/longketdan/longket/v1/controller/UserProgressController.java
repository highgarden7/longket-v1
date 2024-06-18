package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.CommunityApplicationService;
import com.longketdan.longket.v1.controller.dto.UserProgressDTO;
import com.longketdan.longket.v1.service.user.UserProgressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-progress")
@Slf4j
@RequiredArgsConstructor
public class UserProgressController {
    private final CommunityApplicationService communityApplicationService;

    private final UserProgressService userProgressService;

    @GetMapping("/skill/count")
    @Operation(summary = "스킬 전체 카운트 조회", description = "스킬 전체 카운트 조회")
    public ResponseEntity<Object> getAllSkillCount() {
        return CommonResponse.dataListResponseEntity(userProgressService.getAllProgress());
    }

    @GetMapping("/skill/progress/{userId}")
    @Operation(summary = "유저 스킬 진행도 조회", description = "유저 스킬 진행도 조회")
    public ResponseEntity<Object> getUserProgress(@PathVariable(value = "userId") Long userId) {
        return CommonResponse.dataListResponseEntity(userProgressService.getUserProgress(userId));
    }

    @GetMapping("/skill/{categoryId}/progress")
    @Operation(summary = "유저 스킬 진행도 조회", description = "유저 스킬 진행도 조회")
    public ResponseEntity<Object> getUserProgressCount(@PathVariable(value = "categoryId") Long categoryId,
                                                       @RequestParam(value = "categoryType") String categoryType) {
        return CommonResponse.dataResponseEntity(userProgressService.getUserProgressCountWithCategory(categoryId, categoryType));
    }

    @GetMapping("/skill/detail")
    @Operation(summary = "유저 등록 스킬 조회 for 디테일 페이지", description = "유저 등록 스킬 조회 for 디테일 페이지")
    public ResponseEntity<Object> getSkillDetail(@RequestParam(value = "categoryId") Long categoryId,
                                                 @RequestParam(value = "skillId") Long skillId,
                                                 @RequestParam(value = "categoryType") String categoryType) {
        return CommonResponse.dataListResponseEntity(userProgressService.getUserProgressDetail(categoryId, skillId, categoryType));
    }

    @GetMapping("/skill/detail/list")
    @Operation(summary = "다른 유저 인증 스킬 조회 for 디테일 페이지", description = "다른 유저 인증 스킬 조회 for 디테일 페이지")
    public ResponseEntity<Object> getSkillDetailList(@RequestParam(value = "categoryId") Long categoryId,
                                                     @RequestParam(value = "skillId") Long skillId,
                                                     @RequestParam(value = "categoryType") String categoryType,
                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getCommunityDetailList(page, pageSize, categoryId, skillId, categoryType)));
    }

    @GetMapping("/skill/{userId}/list")
    @Operation(summary = "유저 등록 스킬 리스트 조회 for 유저클릭", description = "등록 스킬 리스트 조회 for 유저클릭")
    public ResponseEntity<Object> getUserSkillByUser(@PathVariable(value = "userId") Long userId,
                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getCommunityDetailListByUserId(page, pageSize, userId)));
    }

    @PostMapping("/foot-tricks")
    @Operation(summary = "유저 풋트릭 등록", description = "유저 풋트릭 등록")
    public ResponseEntity<Object> createUserFootTrick(@RequestBody UserProgressDTO.CreateUserFootTrickRequest createRequest) {
        return CommonResponse.dataResponseEntity(userProgressService.createUserProgress(createRequest.toEntity()));
    }

    @GetMapping("/foot-tricks/{userId}/list")
    @Operation(summary = "유저 풋트릭 리스트 조회", description = "유저 트릭 조회")
    public ResponseEntity<Object> getUserFootTrickList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @PathVariable(value = "userId") Long userId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillByUserIdAndType(page, pageSize, userId, "foot_trick")));
    }

    @GetMapping("/foot-tricks/all-user/{footTrickId}/list")
    @Operation(summary = "모든 유저 등록된 풋트릭 리스트 조회", description = "모든 유저의 등록된 풋트릭 조회")
    public ResponseEntity<Object> getUserProgressByFootTrickId(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                               @PathVariable(value = "footTrickId") Long footTrickId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillBySkillIdAndType(page, pageSize, footTrickId, "foot_trick")));
    }

    @PostMapping("/hand-tricks")
    @Operation(summary = "유저 핸드트릭 등록", description = "유저 핸드트릭 등록")
    public ResponseEntity<Object> createUserHandTrick(@RequestBody UserProgressDTO.CreateUserHandTrickRequest createRequest) {
        return CommonResponse.dataResponseEntity(userProgressService.createUserProgress(createRequest.toEntity()));
    }

    @GetMapping("/hand-tricks/{userId}/list")
    @Operation(summary = "유저 핸드트릭 리스트 조회", description = "유저 핸드트릭 조회")
    public ResponseEntity<Object> getUserHandTrickList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @PathVariable(value = "userId") Long userId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillByUserIdAndType(page, pageSize, userId, "hand_trick")));
    }

    @GetMapping("/hand-tricks/all-user/{handTrickId}/list")
    @Operation(summary = "모든 유저 등록된 핸드트릭 리스트 조회", description = "모든 유저의 등록된 핸드트릭 조회")
    public ResponseEntity<Object> getUserProgressByHandTrickId(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                               @PathVariable(value = "handTrickId") Long handTrickId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillBySkillIdAndType(page, pageSize, handTrickId, "hand_trick")));
    }

    @PostMapping("/dancing")
    @Operation(summary = "유저 댄싱 등록", description = "유저 댄싱 등록")
    public ResponseEntity<Object> createUserDancing(@RequestBody UserProgressDTO.CreateUserDancingRequest createRequest) {
        return CommonResponse.dataResponseEntity(userProgressService.createUserProgress(createRequest.toEntity()));
    }

    @GetMapping("/dancing/{userId}/list")
    @Operation(summary = "유저 댄싱 리스트 조회", description = "유저 댄싱 조회")
    public ResponseEntity<Object> getUserDancingList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                     @PathVariable(value = "userId") Long userId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillByUserIdAndType(page, pageSize, userId, "dancing")));
    }

    @GetMapping("/dancing/all-user/{dancingId}/list")
    @Operation(summary = "모든 유저 등록된 댄싱 리스트 조회", description = "모든 유저의 등록된 댄싱 조회")
    public ResponseEntity<Object> getUserProgressByDancingId(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                             @PathVariable(value = "dancingId") Long dancingId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getSkillBySkillIdAndType(page, pageSize, dancingId, "dancing")));
    }

    @PatchMapping("/{userProgressId}/reject")
    @Operation(summary = "유저 스킬 등록 reject", description = "유저 스킬 등록 reject")
    public ResponseEntity<Object> rejectUserProgress(@PathVariable(value = "userProgressId") Long userProgressId) {
        userProgressService.rejectUserProgress(userProgressId);
        return CommonResponse.successResponseEntity();
    }
}
