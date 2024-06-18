package com.longketdan.longket.v1.service.community;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.community.Comment;
import com.longketdan.longket.v1.model.entity.user.User;
import com.longketdan.longket.v1.repository.community.CommentRepository;
import com.longketdan.longket.v1.repository.user.UserRepository;
import com.longketdan.longket.v1.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public int getCommentCountByUserProgressId(Long targetId) {
        return commentRepository.countByTargetId(targetId);
    }

    public boolean hasChild(Long id) {
        return commentRepository.existsByParentCommentId(id);
    }
    public Page<Comment> getCommentByTargetIdAndParentCommentIdIsNull(int page, int pageSize, Long targetId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return commentRepository.findByTargetIdAndParentCommentIdIsNull(targetId, pageable);
    }

    public Page<Comment> getCommentByParentCommentId(int page, int pageSize, Long parentCommentId) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return commentRepository.findByParentCommentId(parentCommentId, pageable);
    }

    public void createComment(Comment comment) {
        User user = userRepository.findById(Util.getUserIdByToken()).orElseThrow(() -> new RuntimeException("User not found."));

        comment.createComment(user.getId());
        commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Long targetId, Comment comment) {
        Comment originComment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if(!originComment.getTargetId().equals(targetId) && originComment.getUserId().equals(Util.getUserIdByToken())) {
            throw new CustomException(ErrorCode.INVALID_COMMENT_UPDATE);
        }

        originComment.setContent(comment.getContent());
        return commentRepository.save(originComment);
    }

    public void deleteComment(Long id) {
        if(commentRepository.existsByParentCommentId(id)) {
            throw new CustomException(ErrorCode.INVALID_COMMENT_DELETE);
        }

        commentRepository.deleteById(id);
    }
}
