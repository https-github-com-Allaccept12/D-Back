package com.example.dplus.repository.post.answer;

import com.example.dplus.domain.post.PostAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAnswerRepository extends JpaRepository<PostAnswer, Long>, PostAnswerRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);

    List<PostAnswer> findAllByPostId(Long postId);
}
