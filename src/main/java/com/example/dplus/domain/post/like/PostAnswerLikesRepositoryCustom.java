package com.example.dplus.domain.post.like;

public interface PostAnswerLikesRepositoryCustom {

    boolean existByAccountIdAndPostAnswerId(Long accountId,Long postAnswerId);
}
