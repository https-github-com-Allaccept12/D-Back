package com.example.dplus.repository.post.comment;

import com.example.dplus.domain.post.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>, PostCommentRepositoryCustom {
    Long countByPostId(Long postId);
    List<PostComment> findAllByPostId(Long postId);
}
