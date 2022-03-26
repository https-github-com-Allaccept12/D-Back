package com.example.dplus.domain.post.comment.like;

public interface PostCommentLikesRepositoryCustom {
    boolean existByAccountIdAndPostCommentId(Long accountId,Long postCommentId);
}
