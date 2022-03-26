package com.example.TeamDPlus.domain.post.like;

public interface PostLikesRepositoryCustom {

    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
