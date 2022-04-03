package com.example.dplus.repository.post;

import com.example.dplus.domain.post.Post;
import com.example.dplus.dto.response.AccountResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 포스트 카테고리별 출력(최신순)
    List<Post> findCategoryPostOrderByCreated(Long lastPostId,String board, String category);

    // 포스트 카테고리별 출력(좋아요순)
    List<Post> findCategoryPostOrderByLikeDesc(Pageable pageable, String board, String category);

    // 조회수 + 라이크 찾기
    List<Post> findPostByMostViewAndMostLike(String board);

    // 검색
    List<Post> findPostBySearchKeyWord(String keyword, Long lastPostId, String board);

    List<Post> findBySimilarPost(String category, String board, Long postId);

    List<AccountResponseDto.MyPost> findPostByAccountIdAndBoard(Long accountId, String board, Pageable pageable);

    List<AccountResponseDto.MyPost> findPostBookMarkByAccountId(Long accountId, String board, Pageable pageable);
}
