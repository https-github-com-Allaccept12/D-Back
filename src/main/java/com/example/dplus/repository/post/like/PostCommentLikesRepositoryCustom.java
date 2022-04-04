package com.example.dplus.repository.post.like;

public interface PostCommentLikesRepositoryCustom {
    boolean existByAccountIdAndPostCommentId(Long accountId,Long postCommentId);
}
