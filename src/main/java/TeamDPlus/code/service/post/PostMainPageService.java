package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostMainPageService {
    // 전체 게시물 조회 (최신순)
    Page<PostResponseDto.PostPageMain> showPostMain(Long accountId, Long postId);

    // 전체 게시물 조회 (좋아요순)
    Page<PostResponseDto.PostPageMain> showPostMainByLikes(Long accountId, Long postId);

    // 게시글 작성
    Long createPost(Account account, PostRequestDto.PostCreateAndUpdate dto);

    // 게시글 검색
    Page<PostResponseDto.PostPageMain> findBySearchKeyWord(String keyword, Long lastArtWorkId);

    // 상세 게시글
    PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId);

    // 게시글 수정
    Long updatePost(Account account, Long postId, PostRequestDto.PostCreateAndUpdate dto);

    // 게시글 삭제
    void deletePost(Long accountId, Long postId);

    // 게시글 추천 피드
    List<PostResponseDto.PostPageMain> showRecommendation(Long account, Long postId);
}
