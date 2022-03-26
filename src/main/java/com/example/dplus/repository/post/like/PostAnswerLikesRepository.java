package com.example.dplus.repository.post.like;

import com.example.dplus.domain.post.PostAnswerLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAnswerLikesRepository extends JpaRepository<PostAnswerLikes, Long>, PostAnswerLikesRepositoryCustom {
    void deleteByPostAnswerIdAndAccountId(Long postAnswerId, Long accountId );
}
