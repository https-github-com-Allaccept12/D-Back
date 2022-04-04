package com.example.dplus.repository.post.like;

import com.example.dplus.domain.post.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositoryCustom {
    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
