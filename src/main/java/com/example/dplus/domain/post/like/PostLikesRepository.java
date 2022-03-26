package com.example.dplus.domain.post.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
