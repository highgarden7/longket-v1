package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.CommunityApplicationService;
import com.longketdan.longket.v1.controller.dto.CommentDTO;
import com.longketdan.longket.v1.controller.dto.LikeDTO;
import com.longketdan.longket.v1.service.community.CommentService;
import com.longketdan.longket.v1.service.community.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityApplicationService communityApplicationService;

    private final CommentService commentService;

//    private final NoticeService noticeService;

    private final LikeService likeService;



//    @GetMapping("/notice/list")
//    @Operation(summary = "공지사항 리스트", description = "공지사항 리스트")
//    public ResponseEntity<Object> getNoticeList() {
//        return ResponseHandler.dataResponseEntity(noticeService.getNoticeList());
//    }

    @GetMapping
    @Operation(summary = "오늘의 스킬 리스트 조회", description = "오늘의 스킬 리스트 조회")
    public ResponseEntity<Object> getCommunityList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getCommunityList(page, pageSize)));
    }

    @GetMapping("/skill/{skillId}")
    @Operation(summary = "커뮤니티 스킬 단건 조회", description = "커뮤니티 스킬 단건 조회")
    public ResponseEntity<Object> getCommunity(@PathVariable(value = "skillId") Long skillId,
                                               @RequestParam(value = "categoryType") String categoryType) {
        return CommonResponse.dataResponseEntity(communityApplicationService.getSkillDetail(skillId, categoryType));
    }

    @GetMapping("/comments")
    @Operation(summary = "커뮤니티 코멘트 리스트 조회", description = "커뮤니티 코멘트 리스트 조회")
    public ResponseEntity<Object> getCommentList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestParam(value = "targetId") Long targetId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getCommentListByTargetId(page, pageSize, targetId)));
    }

    @GetMapping("/comments/{parentCommentId}")
    @Operation(summary = "커뮤니티 답글 리스트 조회", description = "커뮤니티 답글 리스트 조회")
    public ResponseEntity<Object> getChildCommentList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                      @PathVariable(value = "parentCommentId") Long parentCommentId) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(communityApplicationService.getCommentListByParentCommentId(page, pageSize, parentCommentId)));
    }


    @PostMapping("/comments")
    @Operation(summary = "커뮤니티 코멘트 등록", description = "커뮤니티 코멘트 등록")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO.CreateCommentRequest createCommentRequest) {
        commentService.createComment(CommentDTO.CreateCommentRequest.toEntity(createCommentRequest));
        return CommonResponse.successResponseEntity();
    }

    @PatchMapping("/comments/{commentId}/{targetId}")
    @Operation(summary = "커뮤니티 코멘트 수정", description = "커뮤니티 코멘트 수정")
    public ResponseEntity<Object> updateComment(@PathVariable(value = "commentId") Long commentId,
                                                @PathVariable(value = "targetId") Long targetId,
                                                @RequestBody CommentDTO.UpdateCommentRequest updateCommentRequest) {
        return CommonResponse.dataResponseEntity(commentService.updateComment(commentId, targetId, CommentDTO.UpdateCommentRequest.toEntity(updateCommentRequest)));
    }

    @DeleteMapping("/comments/{id}")
    @Operation(summary = "커뮤니티 코멘트 삭제", description = "커뮤니티 코멘트 삭제")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return CommonResponse.successResponseEntity();
    }

    @PostMapping("/likes")
    @Operation(summary = "스킬 좋아요 등록", description = "스킬 좋아요 등록")
    public ResponseEntity<Object> createLike(@RequestBody LikeDTO.LikeRequest likeRequest) {
        return CommonResponse.dataListResponseEntity(likeService.createLike(LikeDTO.LikeRequest.toEntity(likeRequest)));
    }

    @DeleteMapping("/likes/{targetId}")
    @Operation(summary = "스킬 좋아요 삭제", description = "스킬 좋아요 삭제")
    public ResponseEntity<Object> deleteLike(@PathVariable(value = "targetId") Long targetId,
                                             @RequestParam(value = "categoryType") String categoryType) {
        return CommonResponse.dataResponseEntity(likeService.deleteLike(targetId, categoryType));
    }
}
