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
import TeamDPlus.code.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostMainPageServiceImpl implements PostMainPageService{

    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostImageRepository postImageRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PostTagRepository postTagRepository;
    private final FollowRepository followRepository;
    private final PostCommentLikesRepository postCommentLikesRepository;

    private final FileProcessService fileProcessService;


    // 메인 페이지 (최신순)
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMain(Long accountId, Long lastPostId, PostBoard board){

        // 메인 페이지 전체 피드
        Pageable pageable = PageRequest.of(0,12);
        List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByCreatedDesc(lastPostId, pageable, board);
        setCountList(postList);

        // 메인페이지 추천피드
        List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
        setCountList(postRecommendation);
        return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
    }

    // 메인 페이지 (좋아요 순)
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMainByLikes(Long accountId, Long lastPostId, PostBoard board) {

        Pageable pageable = PageRequest.of(0,12);
        List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByPostLikes(lastPostId, pageable, board);
        setCountList(postList);

        // 메인페이지 추천피드
        List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
        setCountList(postRecommendation);
        return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
    }

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

        // 코멘트 좋아요 담을 리스트 생성
        List<CommonDto.IsCommentsLiked> isCommentsLikes = new ArrayList<>();

        // 코멘트 당 좋아요 눌렀는지 체크
        for (int i = 0; i < postComments.size(); i++) {
            boolean isCommentLike = postCommentLikesRepository.existByAccountIdAndPostCommentId(accountId, postComments.get(i).getComment_id());
            isCommentsLikes.get(i).setIsCommentsLiked(isCommentLike);
        }
        return PostResponseDto.PostDetailPage.from(postImageList, postComments,
                postTags, postSubDetail, isLike, isBookmark, isFollow, comment_count, isCommentsLikes);
    }

    // 게시글 작성
    @Transactional
    public int createPost(Account account, PostRequestDto.PostCreate dto, List<MultipartFile> imgFile) {
        if (account.getPostCreateCount() >= 5) {
            throw new ApiRequestException("일일 작성 가능한 게시글분을 다 사용하셨습니다.");
        }
        postWriteValidation(dto);
        Post post = Post.of(account, dto);
        Post savedPost = postRepository.save(post);

        for(int i = 0; i < dto.getImg().size(); i++){
            String img_url = fileProcessService.uploadImage(imgFile.get(i));
            PostImage postImage = PostImage.builder().post(savedPost).postImg(img_url).build();
            postImageRepository.save(postImage);
            log.info(postImage.getPostImg());
        }
        setPostTag(dto.getHashTag(), savedPost);
        return 5 - account.getArtWorkCreateCount();
    }

    // 게시물 수정
    @Transactional
    public Long updatePost(Account account, Long postId, PostRequestDto.PostUpdate dto, List<MultipartFile> imgFile){

        // dto 필드 비었는지 확인
        postUpdateValidation(dto);

        // 수정할 어카운트, 게시물 번호로 권한 확인
        Post post = postAuthValidation(account.getId(), postId);

        // 삭제할 이미지가 있다면, 이미지 주소를 직접 하나씩 지운다
        if(dto.getImg()!=null){
            for(int i = 0; i < dto.getImg().size(); i++){
                String deleteImage = dto.getImg().get(i).getImg_url();
                // 서버 파일 삭제
                fileProcessService.deleteImage(deleteImage);

                // db 삭제
                postImageRepository.deleteByPostImg(deleteImage);
            }
        }

        // 업로드할 이미지가 있다면, 이미지를 업로드한다.
        if(imgFile!=null){
            for(int i = 0; i < imgFile.size(); i++){
                String img_url = fileProcessService.uploadImage(imgFile.get(i));
                PostImage postImage = PostImage.builder().post(post).postImg(img_url).build();
                postImageRepository.save(postImage);
            }
        }

        // 게시글을 업데이트 한다
        post.updatePost(dto);

        // 태그도 지우고 다시 세팅
        postTagRepository.deleteAllByPostId(postId);
        setPostTag(dto.getHashTag(), post);
        return post.getId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long accountId, Long postId){
        Post post = postAuthValidation(accountId, postId);
        List<PostImage> postImages = postImageRepository.findByPostId(postId);
        postImages.forEach((img) -> {
            // S3 이미지 삭제
            fileProcessService.deleteImage(img.getPostImg());
        });
        // db 삭제
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

    private void setCountList(List<PostResponseDto.PostPageMain> postList){
        postList.forEach((post) -> {
            Long bookmark_count = postBookMarkRepository.countByPostId(post.getPost_id());
            Long comment_count = postCommentRepository.countByPostId(post.getPost_id());
            post.setCountList(bookmark_count, comment_count);
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
    private Post postAuthValidation(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!post.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return post;
    }

    // 게시글작성 필수요소 validation
    private void postWriteValidation(PostRequestDto.PostCreate dto){
        if(Objects.equals(dto.getTitle(), "")){
            throw new ApiRequestException("제목을 입력하세요");
        }
        if(Objects.equals(dto.getContent(), "")){
            throw new ApiRequestException("내용을 입력하세요");
        }
        if(Objects.equals(dto.getCategory(), "")){
            throw new ApiRequestException("카테고리를 입력하세요");
        }
        if(Objects.equals(dto.getBoard(), "")){
            throw new ApiRequestException("디모 게시판 종류를 선택하세요");
        }
    }

    // 게시글수정 필수요소 validation
    private void postUpdateValidation(PostRequestDto.PostUpdate dto){
        if(Objects.equals(dto.getTitle(), "")){
            throw new ApiRequestException("제목을 입력하세요");
        }
        if(Objects.equals(dto.getContent(), "")){
            throw new ApiRequestException("내용을 입력하세요");
        }
        if(Objects.equals(dto.getCategory(), "")){
            throw new ApiRequestException("카테고리를 입력하세요");
        }
    }

}