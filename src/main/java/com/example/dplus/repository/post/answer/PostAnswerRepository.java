package com.example.dplus.repository.post.answer;

import com.example.dplus.domain.post.PostAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAnswerRepository extends JpaRepository<PostAnswer, Long>, PostAnswerRepositoryCustom {
    Long countByPostId(Long postId);
}
