package com.example.dplus.repository.post.like;

public interface PostAnswerLikesRepositoryCustom {

    boolean existByAccountIdAndPostAnswerId(Long accountId,Long postAnswerId);
}
