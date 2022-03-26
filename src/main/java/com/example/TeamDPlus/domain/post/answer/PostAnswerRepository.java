package com.example.TeamDPlus.domain.post.answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAnswerRepository extends JpaRepository<PostAnswer, Long>, PostAnswerRepositoryCustom {
    Long countByPostId(Long postId);
}
