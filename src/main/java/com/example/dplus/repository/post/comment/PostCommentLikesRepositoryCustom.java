package com.example.dplus.repository.post.comment;

public interface PostCommentLikesRepositoryCustom {
    boolean existByAccountIdAndPostCommentId(Long accountId,Long postCommentId);
}
