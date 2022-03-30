package com.example.dplus.repository.post.bookmark;

import com.example.dplus.domain.post.PostBookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostBookMarkRepository extends JpaRepository<PostBookMark, Long>, PostBookMarkRepositoryCustom {
    void deleteAllByPostId(Long postId);

    @Query("select b.id from PostBookMark b where b.post.id =:postId")
    List<Long> findBookMarkByPostId(@Param("postId") Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
