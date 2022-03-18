package TeamDPlus.code.domain.post;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    // 포스트 메인페이지 출력 (최신순)
    Page<PostResponseDto.PostPageMain> findAllPostOrderByCreatedDesc(Long lastPostId, Pageable pageable);

//    // 포스트 메인페이지 출력 (좋아요)
    Page<PostResponseDto.PostPageMain> findAllPostOrderByPostLikes(Long lastPostId, Pageable pageable);

    // 상세페이지 서브
    PostResponseDto.PostSubDetail findByPostSubDetail (Long postId);

    // 조회수 + 라이크 찾기
    List<PostResponseDto.PostPageMain> findPostByMostViewAndMostLike();

    // 검색
    Page<PostResponseDto.PostPageMain> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable);

    PostResponseDto.PostAnswerSubDetail findByPostAnswerSubDetail(Long postId);

//    List<PostResponseDto.PostSimilarQuestion> findByCategory(String category);
}
