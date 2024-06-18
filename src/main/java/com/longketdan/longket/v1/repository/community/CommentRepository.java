package com.longketdan.longket.v1.repository.community;

import com.longketdan.longket.v1.model.entity.community.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByTargetId(Long targetId);
    Page<Comment> findByTargetIdAndParentCommentIdIsNull(Long targetId, Pageable pageable);

    Page<Comment> findByParentCommentId(Long parentCommentId, Pageable pageable);

    boolean existsByParentCommentId(Long parentCommentId);

    List<Comment> findByTargetIdAndParentCommentIdIsNotNull(Long targetId);

    Optional<Comment> findByIdAndTargetId(Long id, Long targetId);
}
