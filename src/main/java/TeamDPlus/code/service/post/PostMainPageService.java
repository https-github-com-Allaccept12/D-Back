package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostBoard;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostMainResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostMainPageService {
    // 전체 게시물 조회 (최신순)
    PostMainResponseDto showPostMain(Long accountId, Long lastPostId, PostBoard board);

    // 전체 게시물 조회 (좋아요순)
//    Page<PostResponseDto.PostPageMain> showPostMainByLikes(Long accountId, Long postId);

    // 게시글 작성
    Long createPost(Account account, PostRequestDto.PostCreate dto);

    // 게시글 검색
    Page<PostResponseDto.PostPageMain> findBySearchKeyWord(String keyword, Long lastArtWorkId);

    // 상세 게시글
    PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId);

    // 게시글 수정
    Long updatePost(Account account, Long postId, PostRequestDto.PostUpdate dto);

    // 게시글 삭제
    void deletePost(Long accountId, Long postId);

    // 코멘트 생성
    Long createPostComment(Account account, Post post, PostRequestDto.PostComment dto);

    // 코멘트 삭제
    void deletePostComment(Long accountId, Long postCommentId);

    // 코멘트 수정
    Long updatePostComment(Long accountId, Long postCommentId, PostRequestDto.PostComment dto);
}
