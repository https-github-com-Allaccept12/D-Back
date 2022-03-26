package com.example.dplus.repository.post.like;

public interface PostLikesRepositoryCustom {

    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
