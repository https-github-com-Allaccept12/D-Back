package com.example.dplus.service.post;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.Post;
import com.example.dplus.domain.post.PostImage;
import com.example.dplus.domain.post.PostTag;
import com.example.dplus.dto.common.CommonDto;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.dto.response.PostMainReccomendationDto;
import com.example.dplus.dto.request.PostRequestDto.PostUpdate;
import com.example.dplus.dto.response.PostMainResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import com.example.dplus.dto.response.PostResponseDto.*;
import com.example.dplus.dto.response.PostSearchResponseDto;
import com.example.dplus.repository.BatchInsertRepository;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.follow.FollowRepository;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.comment.PostCommentLikesRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.repository.post.image.PostImageRepository;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import com.example.dplus.repository.post.like.PostLikesRepository;
import com.example.dplus.repository.post.tag.PostTagRepository;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final PostAnswerRepository postAnswerRepository;
    private final FollowRepository followRepository;
    private final PostCommentLikesRepository postCommentLikesRepository;
    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final FileProcessService fileProcessService;
    private final AccountRepository accountRepository;
    private final BatchInsertRepository batchInsertRepository;

    // 디모페이지 추천피드
    @Transactional(readOnly = true)
    public PostMainReccomendationDto showPostRecommendation(String board){
        List<Post> postRecommendation = postRepository.findPostByMostViewAndMostLike(board);
        return PostMainReccomendationDto.from(postRecommendation);
    }

    // 디모페이지 카테고리별 최신순
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMain(Long lastPostId, String board, String category) {
        List<Post> postList = postRepository.findCategoryPostOrderByCreated(lastPostId, board, category);
        return PostMainResponseDto.from(postList);
    }

    // 디모페이지 카테고리별 좋아요
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMainLikeSort(int start, String board, String category) {
        Pageable pageable = PageRequest.of(start, 12);
        List<Post> postList = postRepository.findCategoryPostOrderByLikeDesc(pageable, board, category);
        return PostMainResponseDto.from(postList);
    }

    // 상세 게시글 (디플 - 정보공유)
    @Transactional(readOnly = true)
    public PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        post.addViewCount();
        List<PostImage> postImageList = postImageRepository.findByPostId(postId);
        List<Long> bookmarks = postBookMarkRepository.findBookMarkByPostId(postId);
        List<PostComment> postComments = postCommentRepository.findPostCommentByPostId(postId);

        postComments.forEach((comment) -> {
            Boolean commentLike = postCommentLikesRepository.existByAccountIdAndPostCommentId(accountId, comment.getComment_id());
            comment.setIs_comment_like(commentLike);
        });
        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;
        if(accountId != 0){
            isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
            isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, post.getAccount().getId());
        }
        return PostResponseDto.PostDetailPage.of(postImageList, postComments,
                post, isLike, isBookmark, isFollow, (long) postComments.size(), (long) bookmarks.size());
    }

    // 게시글 작성
    @Transactional
    public int createPost(Long accountId, PostRequestDto.PostCreate dto, List<MultipartFile> imgFile) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        if (account.getPostCreateCount() >= 1000) {
            throw new ErrorCustomException(ErrorCode.DAILY_POST_WRITE_UP_BURN_ERROR);
        }
        Post post = Post.of(account, dto);
        Post savedPost = postRepository.save(post);
        setPostImage(imgFile, post);
        setPostTag(dto.getHash_tag(), savedPost);
        account.upPostCountCreate();
        return 5 - account.getPostCreateCount();
    }

    // 게시물 수정
    @Transactional
    public Long updatePost(Long accountId, Long postId, PostUpdate dto, List<MultipartFile> imgFile){
        Post post = postAuthValidation(accountId, postId);
        // 삭제할 이미지가 있다면, 이미지 주소를 직접 하나씩 지운다
        if(dto.getDelete_img()!=null){
            for(int i = 0; i < dto.getDelete_img().size(); i++){
                String deleteImage = dto.getDelete_img().get(i).getFilename();
                fileProcessService.deleteImage(deleteImage);
                postImageRepository.deleteByPostImg(deleteImage);
            }
        }
        setPostImage(imgFile, post);
        if(dto.getHash_tag()!=null){
            postTagRepository.deleteAllByPostId(postId);
            setPostTag(dto.getHash_tag(), post);
        }
        post.updatePost(dto);
        return post.getId();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long accountId, Long postId, String board){
        Post post = postAuthValidation(accountId, postId);
        List<PostImage> postImages = postImageRepository.findByPostId(postId);
        postImages.forEach((img) -> {
            fileProcessService.deleteImage(img.getPostImg());
        });
        if (board.equals("QNA")){
            List<com.example.dplus.domain.post.PostAnswer> postAnswerList = postAnswerRepository.findAllByPostId(postId);
            postAnswerList.forEach((postAnswer) ->
                    postAnswerLikesRepository.deleteAllByPostAnswerId(postAnswer.getId())
            );
            postAnswerRepository.deleteAllByPostId(postId);
        }
        postImageRepository.deleteAllByPostId(postId);
        postBookMarkRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    // 게시글 검색
    @Transactional(readOnly = true)
    public PostSearchResponseDto findBySearchKeyWord(String keyword, Long lastPostId, String board) {
        List<Post> postLists = postRepository.findPostBySearchKeyWord( keyword,  lastPostId, board);
        return PostSearchResponseDto.from(postLists);
    }

    // 디모 QnA 상세페이지
    @Transactional(readOnly = true)
    public PostAnswerDetailPage detailAnswer(Long accountId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        post.addViewCount();
        // 질문 이미지들 가져오기
        List<PostImage> imgList = postImageRepository.findByPostId(postId);
        // 질문 답변 가져오기
        List<PostAnswer> postAnswerList = postAnswerRepository.findPostAnswerByPostId(postId);
        // 북마크 수
        List<Long> bookMarkCount = postBookMarkRepository.findBookMarkByPostId(postId);
        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;
        if (accountId != 0) {
            // 지금 상세페이지를 보고있는 사람이 좋아요를 했는지
            isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
            // 지금 상세페이지를 보고있는 사람이 북마크를 했는지
            isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
            // 지금 상세페이지를 보고있는 사람이 팔로우를 했는지
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, post.getAccount().getId());
            // 지금 상세페이지를 보고있는 사람이 답글 좋아요를 했는지
            setIsLike(accountId, postAnswerList);
            // 지금 상세페이지를 보고있는 사람이 답글 팔로우를 했는지
            isFollow(accountId, postAnswerList);
        }
        return PostAnswerDetailPage.from(imgList, postAnswerList,post, isLike, isBookmark, isFollow, (long) bookMarkCount.size());
    }

    // 디모 QnA 유사한질문 조회
    @Transactional(readOnly = true)
    public List<PostSimilarQuestion> similarQuestion(String category, Long postId) {
        List<Post> postSimilarList = postRepository.findBySimilarPost(category, "QNA", postId);
        List<PostSimilarQuestion> result = postSimilarList.stream()
                .map(PostSimilarQuestion::of)
                .collect(Collectors.toList());
        result.forEach((postSimilar) -> {
            Long answerCount = postAnswerRepository.countByPostId(postSimilar.getPost_id());
            postSimilar.setAnswer_count(answerCount);
        });
        return result;
    }

    private void setPostImage(List<MultipartFile> imgFile, Post post) {
        if(imgFile !=null){
            List<PostImage> imgList = new ArrayList<>();
            imgFile.forEach((file) -> {
                String img_url = fileProcessService.uploadImage(file);
                PostImage postImage = PostImage.builder().post(post).postImg(img_url).build();
                imgList.add(postImage);
            });
            batchInsertRepository.postImageSaveAll(imgList);
        }
    }

    // #단위로 끊어서 해쉬태그 들어옴 (dto -> 받을 때)
    private void  setPostTag(List<CommonDto.PostTagDto> dto, Post post){
        if (dto.size() != 0) {
            List<PostTag> tagList = new ArrayList<>();
            dto.forEach((tag) -> {
                PostTag postTag = PostTag.builder()
                        .post(post)
                        .hashTag(tag.getTag())
                        .build();
                tagList.add(postTag);
            });
            batchInsertRepository.postTagImageSaveAll(tagList);
        }
    }
    // post 수정, 삭제 권한 확인
    private Post postAuthValidation(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if(!post.getAccount().getId().equals(accountId)){
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return post;
    }
    private void isFollow(Long accountId, List<PostAnswer> postAnswerList) {
        postAnswerList.forEach((postAnswer) -> {
            boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, postAnswer.getAccount_id());
            if (isFollow)
                postAnswer.setIsFollow(true);
        });
    }

    private void setIsLike(Long accountId, List<PostAnswer> postAnswerList) {
        postAnswerList.forEach((postAnswer) -> {
            if(postAnswerLikesRepository.existByAccountIdAndPostAnswerId(accountId, postAnswer.getAnswer_id())) {
                postAnswer.setLikeCountAndIsLike(true);
            }
        });
    }

}