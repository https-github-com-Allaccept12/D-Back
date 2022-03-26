package com.example.TeamDPlus.domain.post;

import com.example.TeamDPlus.dto.response.AccountResponseDto;
import com.example.TeamDPlus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 포스트 메인페이지 출력 (최신순, 좋아요)
    List<PostResponseDto.PostPageMain> findAllPostOrderByCreatedDesc(Long lastPostId, Pageable pageable, PostBoard board, int sortSign, String category);

    // 상세페이지 서브
    PostResponseDto.PostSubDetail findByPostSubDetail (Long postId);

    // 조회수 + 라이크 찾기
    List<PostResponseDto.PostPageMain> findPostByMostViewAndMostLike();

    // 검색
    List<PostResponseDto.PostPageMain> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable, PostBoard board);

    PostResponseDto.PostAnswerSubDetail findByPostAnswerSubDetail(Long postId);

    List<PostResponseDto.PostSimilarQuestion> findByCategory(String category, String board, Long postId);

    List<AccountResponseDto.MyPost> findPostByAccountIdAndBoard(Long accountId, String board, Pageable pageable);

    List<AccountResponseDto.MyPost> findPostBookMarkByAccountId(Long accountId, String board, Pageable pageable);
}
