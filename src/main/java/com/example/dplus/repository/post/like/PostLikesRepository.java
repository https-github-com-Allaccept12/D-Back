package com.example.dplus.repository.post.like;

import com.example.dplus.domain.post.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
