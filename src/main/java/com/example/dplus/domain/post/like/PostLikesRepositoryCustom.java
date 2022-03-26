package com.example.dplus.domain.post.like;

public interface PostLikesRepositoryCustom {

    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
