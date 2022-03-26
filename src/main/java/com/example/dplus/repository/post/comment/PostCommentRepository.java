package com.example.dplus.repository.post.comment;

import com.example.dplus.domain.post.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>, PostCommentRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);
}
