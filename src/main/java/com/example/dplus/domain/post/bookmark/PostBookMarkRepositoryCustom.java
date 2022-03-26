package com.example.dplus.domain.post.bookmark;

public interface PostBookMarkRepositoryCustom {
    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
