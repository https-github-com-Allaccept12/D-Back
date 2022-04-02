package com.example.dplus.repository.post;

import com.example.dplus.domain.post.Post;
import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 포스트 메인페이지 출력 (최신순, 좋아요)
    List<Post> findAllPostOrderByCreatedDesc(Long lastPostId, String board, String category);

    List<Post> findAllPostOrderByLikeDesc(Pageable pageable, String board, String category);

    // 조회수 + 라이크 찾기
    List<Post> findPostByMostViewAndMostLike();

    // 검색

    List<Post> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable, String board);

    List<Post> findBySimilarPost(String category, String board, Long postId);

    List<AccountResponseDto.MyPost> findPostByAccountIdAndBoard(Long accountId, String board, Pageable pageable);

    List<AccountResponseDto.MyPost> findPostBookMarkByAccountId(Long accountId, String board, Pageable pageable);
}
