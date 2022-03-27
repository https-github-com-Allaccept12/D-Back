package com.example.dplus.repository.post.bookmark;

import com.example.dplus.domain.post.PostBookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostBookMarkRepository extends JpaRepository<PostBookMark, Long>, PostBookMarkRepositoryCustom {
    Long countByPostId(Long postId);

    void deleteAllByPostId(Long postId);

    List<PostBookMark> findByPostId(Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
