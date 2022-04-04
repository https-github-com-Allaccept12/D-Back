package com.example.dplus.repository.post.comment;

import com.example.dplus.domain.post.PostCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikesRepository extends JpaRepository<PostCommentLikes, Long>, PostCommentLikesRepositoryCustom {
    void deleteByPostCommentIdAndAccountId(Long postCommentId, Long accountId);

    void deleteAllByPostCommentId(Long postCommentId);
}
