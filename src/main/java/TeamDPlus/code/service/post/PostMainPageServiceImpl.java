package TeamDPlus.code.service.post;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostBoard;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikes;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikesRepository;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.domain.post.tag.PostTagRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostMainResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostMainPageServiceImpl implements PostMainPageService{

    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostImageRepository postImageRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PostTagRepository postTagRepository;
    private final FollowRepository followRepository;
    private final PostCommentLikesRepository postCommentLikesRepository;


    // 메인 페이지 (최신순)
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMain(Long accountId, Long lastPostId, PostBoard board){
        
        // 메인 페이지 전체 피드
        Pageable pageable = PageRequest.of(0,12);
        Page<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByCreatedDesc(lastPostId, pageable);
        setCountList(postList);

        // 메인페이지 추천피드
        List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
        postList.forEach((post) -> {
            Long bookmark_count = postBookMarkRepository.countByPostId(post.getPost_id());
            Long comment_count = postCommentRepository.countByPostId(post.getPost_id());
            Long like_count = postLikesRepository.countByPostId(post.getPost_id());
            post.setCountList(bookmark_count, comment_count, like_count);
        });
        return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
    }

//    // 메인 페이지 (좋아요 순)
//    @Transactional(readOnly = true)
//    public Page<PostResponseDto.PostPageMain> showPostMainByLikes(Long accountId, Long lastPostId) {
//        Pageable pageable = PageRequest.of(0,12);
//        Page<PostResponseDto.PostPageMain> post인ist = postRepository.findAllPostOrderByPostLikes(lastPostId, pageable);
//        setCountList(accountId, postList);
//        return postList;
//    }

    // 상세 게시글 (디플 - 꿀팁)
    @Transactional(readOnly = true)
    public PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        post.addViewCount();
        PostResponseDto.PostSubDetail postSubDetail = postRepository.findByPostSubDetail(postId);
        List<PostImage> postImageList = postImageRepository.findByPostId(postId);
        List<PostResponseDto.PostComment> postComments = postCommentRepository.findPostCommentByPostId(postId);
        List<PostTag> postTags = postTagRepository.findPostTagsByPostId(postId);
        boolean isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
        boolean isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
        boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, postSubDetail.getAccount_id());
        Long comment_count = (long) postComments.size();
        return PostResponseDto.PostDetailPage.from(postImageList, postComments,
                postTags, postSubDetail, isLike, isBookmark, isFollow, comment_count);
    }

    // 게시글 작성
    @Transactional
    public Long createPost(Account account, PostRequestDto.PostCreate dto) {
        Post post = Post.of(account, dto);
        Post savedPost = postRepository.save(post);
        setPostTag(dto.getHashTag(), savedPost);
        setImgUrl(dto.getImg(), savedPost);
        return post.getId();
    }

    // 게시물 수정
    @Transactional
    public Long updatePost(Account account, Long postId, PostRequestDto.PostUpdate dto){
        Post post = postValidation(account.getId(), postId);
        post.updatePost(dto);
        postImageRepository.deleteAllByPostId(postId);
        setImgUrl(dto.getImg(), post);
        postTagRepository.deleteAllByPostId(postId);
        setPostTag(dto.getHashTag(), post);
        return post.getId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long accountId, Long postId){
        Post post = postValidation(accountId, postId);
        postLikesRepository.deleteAllByPostId(postId);
        postTagRepository.deleteAllByPostId(postId);
        postImageRepository.deleteAllByPostId(postId);
        postBookMarkRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    // 게시글 검색
    @Transactional(readOnly = true)
    public Page<PostResponseDto.PostPageMain> findBySearchKeyWord(String keyword, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,5);
        return postRepository.findPostBySearchKeyWord(keyword,lastArtWorkId,pageable);
    }

    private void setCountList(Page<PostResponseDto.PostPageMain> postList){
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

    // 댓글 수정, 삭제 권한 확인
    private PostComment commentValidation(Long accountId, Long postCommentId){
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ApiRequestException("해당 댓글은 존재하지 않습니다."));
        if(!postComment.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return postComment;
    }

    // 코멘트 생성
    @Transactional
    public Long createPostComment(Account account, Post post, PostRequestDto.PostComment dto) {
        PostComment postComment = PostComment.of(account, post, dto);
        PostComment savedComment = postCommentRepository.save(postComment);
        return savedComment.getId();
    }

    // 코멘트 삭제
    @Transactional
    public void deletePostComment(Long accountId, Long postCommentId) {
        PostComment postComment = commentValidation(accountId, postCommentId);
        // 코멘트에 달린 좋아요 삭제
        postCommentLikesRepository.deleteAllByPostCommentId(postCommentId);
        // 코멘트 삭제
        postCommentRepository.deleteById(postComment.getId());
    }

    // 코멘트 수정
    @Transactional
    public Long updatePostComment(Long accountId, Long postCommentId, PostRequestDto.PostComment dto) {
        PostComment postComment = commentValidation(accountId, postCommentId);
        postComment.updateComment(dto);
        return postComment.getId();
    }
}
