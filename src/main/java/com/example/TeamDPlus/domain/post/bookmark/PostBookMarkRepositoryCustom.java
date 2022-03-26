package com.example.TeamDPlus.domain.post.bookmark;

public interface PostBookMarkRepositoryCustom {
    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
