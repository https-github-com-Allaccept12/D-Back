package com.example.dplus.repository.post.bookmark;

public interface PostBookMarkRepositoryCustom {
    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
