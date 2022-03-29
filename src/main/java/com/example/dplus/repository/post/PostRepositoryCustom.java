package com.example.dplus.repository.post;

import com.example.dplus.domain.post.PostBoard;
import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 포스트 메인페이지 출력 (최신순)
    List<PostResponseDto.PostPageMain> findAllPostOrderByCreatedDesc(Long lastPostId, Pageable pageable, String board, String category);

    // 포스트 메인페이지 출력(좋아요)
    List<PostResponseDto.PostPageMain> findAllPostOrderByLikes(Pageable pageable, String board, String category);

    // 상세페이지 서브
    PostResponseDto.PostSubDetail findByPostSubDetail (Long postId);

    // 조회수 + 라이크 찾기
    List<PostResponseDto.PostPageMain> findPostByMostViewAndMostLike();

    // 검색
    List<PostResponseDto.PostSearchMain> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable, String board);

    PostResponseDto.PostAnswerSubDetail findByPostAnswerSubDetail(Long postId);

    List<PostResponseDto.PostSimilarQuestion> findByCategory(String category, String board, Long postId);

    List<AccountResponseDto.MyPost> findPostByAccountIdAndBoard(Long accountId, String board, Pageable pageable);

    List<AccountResponseDto.MyPost> findPostBookMarkByAccountId(Long accountId, String board, Pageable pageable);
}
