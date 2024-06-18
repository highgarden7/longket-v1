package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.ChallengeApplicationService;
import com.longketdan.longket.v1.controller.dto.ChallengeDTO;
import com.longketdan.longket.v1.service.challenge.ChallengeService;
import com.longketdan.longket.v1.service.community.CommentService;
import com.longketdan.longket.v1.service.community.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    private final ChallengeApplicationService challengeApplicationService;

    private final CommentService commentService;

    private final LikeService likeService;

    @GetMapping("/list")
    @Operation(summary = "챌린지 리스트 조회", description = "챌린지 리스트 조회")
    public ResponseEntity<Object> getChallengeList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(challengeApplicationService.getChallengeList(page, pageSize)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "챌린지 단건 조회", description = "챌린지 단건 조회")
    public ResponseEntity<Object> getChallenge(@PathVariable(value = "id") Long id) {
        return CommonResponse.dataResponseEntity(challengeApplicationService.getChallengeDetail(id));
    }

    @PostMapping()
    @Transactional
    @Operation(summary = "챌린지 등록", description = "챌린지 등록")
    public ResponseEntity<Object> createChallenge(@RequestBody ChallengeDTO.CreateChallengeRequest challenge) {
        challengeApplicationService.createChallenge(challenge);
        return CommonResponse.successResponseEntity();
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "챌린지 수정", description = "챌린지 수정")
    public ResponseEntity<Object> updateChallenge(@PathVariable(value = "id") Long id,
                                                  @RequestBody ChallengeDTO.UpdateChallengeRequest challenge) {
        challengeApplicationService.updateChallenge(id, challenge);
        return CommonResponse.successResponseEntity();
    }

    @PatchMapping("/{id}/close")
    @Transactional
    @Operation(summary = "챌린지 종료", description = "챌린지 종료")
    public ResponseEntity<Object> closeChallenge(@PathVariable(value = "id") Long id,
                                                 @RequestBody ChallengeDTO.CloseChallengeRequest closeChallengeRequest) {
        challengeApplicationService.closeChallenge(id, closeChallengeRequest);
        return CommonResponse.successResponseEntity();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "챌린지 삭제", description = "챌린지 삭제")
    public ResponseEntity<Object> deleteChallenge(@PathVariable(value = "id") String id) {
        challengeService.deleteChallenge(id);
        return CommonResponse.successResponseEntity();
    }

    @PostMapping("/{id}/join")
    @Transactional
    @Operation(summary = "챌린지 참여", description = "챌린지 참여")
    public ResponseEntity<Object> joinChallenge(@PathVariable(value = "id") Long id,
                                                @RequestBody ChallengeDTO.CreateChallengerRequest createChallengerRequest) {
        challengeApplicationService.joinChallenge(id, createChallengerRequest);
        return CommonResponse.successResponseEntity();
    }

    @PatchMapping("/{id}/out")
    @Transactional
    @Operation(summary = "챌린지 탈퇴", description = "챌린지 탈퇴")
    public ResponseEntity<Object> outChallenge(@PathVariable(value = "id") Long id,
                                               @RequestParam(value = "userId") Long userId) {
        challengeService.outChallenge(id, userId);
        return CommonResponse.successResponseEntity();
    }

//    @PostMapping("/likes")
//    @Operation(summary = "챌린지 좋아요 등록", description = "챌린지 좋아요 등록")
//    public ResponseEntity<Object> insertLike(@RequestParam(value = "challengeId") String challengeId) {
////        return ResponseHandler.dataResponseEntity(challengeService.insertLike(challengeId));
//        return null;
//    }
//
//    @DeleteMapping("/likes/{id}")
//    @Operation(summary = "챌린지 좋아요 삭제", description = "챌린지 좋아요 삭제")
//    public ResponseEntity<Object> deleteLike(@PathVariable(value = "id") String id,
//                                             @RequestParam(value = "challengeId") String challengeId) {
////        return ResponseHandler.dataResponseEntity(challengeService.deleteLike(id, challengeId));
//        return null;
//
//    }
//
//    @PostMapping("/comments")
//    @Operation(summary = "챌린지 코멘트 등록", description = "챌린지 코멘트 등록")
//    public ResponseEntity<Object> insertComment(@RequestBody CommentDTO.CreateCommentRequest createCommentRequest) {
//        commentService.createComment(CommentDTO.CreateCommentRequest.toEntity(createCommentRequest));
//        return ResponseHandler.successResponseEntity();
//    }
//
//    @PatchMapping("/comments/{commentId}/{targetId}")
//    @Operation(summary = "챌린지 코멘트 수정", description = "챌린지 코멘트 수정")
//    public ResponseEntity<Object> updateComment(@PathVariable(value = "commentId") Long commentId,
//                                                  @PathVariable(value = "targetId") Long targetId,
//                                                @RequestBody CommentDTO.UpdateCommentRequest updateCommentRequest) {
//        return ResponseHandler.dataResponseEntity(commentService.updateComment(commentId, targetId, CommentDTO.UpdateCommentRequest.toEntity(updateCommentRequest)));
//    }
//
//    @DeleteMapping("/comments/{id}")
//    @Operation(summary = "챌린지 코멘트 삭제", description = "챌린지 코멘트 삭제")
//    public ResponseEntity<Object> deleteComment(@PathVariable(value = "id") Long id) {
//        commentService.deleteComment(id);
//        return ResponseHandler.successResponseEntity();
//    }
}
