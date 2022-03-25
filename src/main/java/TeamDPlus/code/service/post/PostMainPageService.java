package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostBoard;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostMainResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostMainPageService {
    // 전체 게시물 조회 (최신순)
    PostMainResponseDto showPostMain(Long accountId, Long lastPostId, PostBoard board, String category, int sortSign);

    // 게시글 작성
    int createPost(Long accountId, PostRequestDto.PostCreate dto, List<MultipartFile> imageFile);

    // 게시글 검색
    List<PostResponseDto.PostPageMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId, PostBoard board);

    // 상세 게시글
    PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId);

    // 게시글 수정
    Long updatePost(Account account, Long postId, PostRequestDto.PostUpdate dto, List<MultipartFile> imgFile);

    // 게시글 삭제
    void deletePost(Long accountId, Long postId);

    // 상세 질문글
    PostResponseDto.PostAnswerDetailPage detailAnswer(Long accountId, Long postId);

    // 유사한 질문 리스트
    List<PostResponseDto.PostSimilarQuestion> similarQuestion(String category, Long accountId, Long postId);

}
