package TeamDPlus.code.service.post;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.domain.post.tag.PostTagRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMainPageServiceImpl implements PostMainPageService{

    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostImageRepository postImageRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PostTagRepository postTagRepository;
    private final PostAnswerRepository postAnswerRepository;
    private final FollowRepository followRepository;

    // 전체 페이지
    @Transactional(readOnly = true)
    public Page<PostResponseDto.PostPageMain> showPostMain(Long accountId, Long lastPostId) {
        Pageable pageable = PageRequest.of(0,12);
        Page<PostResponseDto.PostPageMain> postList = postRepository.findAllPost(lastPostId, pageable);
        setCountList(accountId, postList);
        return postList;
    }

    // 게시글 작성
    @Transactional
    public Long createPost(Account account, PostRequestDto.PostCreateAndUpdate dto) {
        Post post = Post.of(account, dto);
        Post savedPost = postRepository.save(post);
        setPostTag(dto.getHashTag(), savedPost);
        setImgUrl(dto.getImg(), savedPost);
        return post.getId();
    }

    // 게시글 삭제

    // 게시글 검색
    @Transactional(readOnly = true)
    public Page<PostResponseDto.PostPageMain> findBySearchKeyWord(String keyword, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,5);
        return postRepository.findPostBySearchKeyWord(keyword,lastArtWorkId,pageable);
    }

    private void setCountList(Long accountId, Page<PostResponseDto.PostPageMain> postList){
        postList.forEach((post) -> {
            Long bookmark_count = postBookMarkRepository.countByPostId(post.getPost_id());
            Long comment_count = postCommentRepository.countByPostId(post.getPost_id());
            Long like_count = postLikesRepository.countByPostId(post.getPost_id());
            post.setCountList(bookmark_count, comment_count, like_count);
        });
    }

    private void setImgUrl(List<CommonDto.ImgUrlDto> dto, Post post) {
        dto.forEach((img) -> {
            PostImage postImage = PostImage.builder()
                    .post(post)
                    .postImg(img.getImg_url())
                    .build();
            postImageRepository.save(postImage);
        });
    }

    // #단위로 끊어서 해쉬태그 들어옴
    private void setPostTag(List<CommonDto.PostTagDto> dto, Post post){
        dto.forEach((tag) -> {
            PostTag postTag = PostTag.builder()
                    .post(post)
                    .hashTag(tag.getHashTag())
                    .build();
            postTagRepository.save(postTag);
        });
    }

    // post 수정, 삭제 권한 확인
    private Post postValidation(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!post.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return post;
    }

    // 로그인한 사용자의 좋아요 표시




    // 디모 QnA 상세페이지
    @Transactional(readOnly = true)
    public PostResponseDto.PostAnswerDetailPage detailAnswer(Long accountId, Long postId) {
        // 작품 게시글 존재여부
        Post post = postValidation(accountId, postId);
        // 조회수 + 1
        post.addViewCount();
        // QnA 좋아요 개수와 QnA 기본정보 가져오기
        PostResponseDto.PostSubDetail postSubDetail = postRepository.findByPostSubDetail(accountId, postId);
        // 작품 이미지들 가져오기
        List<PostImage> imgList = postImageRepository.findByPostId(postId);
        // 질문 답변 가져오기
        List<PostResponseDto.PostAnswer> postAnswerList = postAnswerRepository.findPostAnswerByPostId(postId);
        // 태그 리스트 가져오기
        List<PostTag> postTagList = postTagRepository.findByPostId(postId);
        // 좋아요 수
        Long likeCount = postLikesRepository.countByPostId(postId);
        // 북마크 수
        Long bookMarkCount = postBookMarkRepository.countByPostId(postId);
        // 답변 수
        Long answerCount = postAnswerRepository.countByPostId(postId);
        // 지금 상세페이지를 보고있는 사람이 좋아요를 했는지
        boolean isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
        // 지금 상세페이지를 보고있는 사람이 북마크를 했는지
        boolean isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
        // 지금 상세페이지를 보고있는 사람이 팔로우를 했는지
        boolean isFollow = followRepository.existsByFollowerIdAndAndFollowingId(accountId, postSubDetail.getAccount_id());
        return PostResponseDto.PostAnswerDetailPage.from(imgList, postAnswerList, postTagList, postSubDetail, isLike, isBookmark, isFollow, likeCount, bookMarkCount, answerCount);
    }
}
