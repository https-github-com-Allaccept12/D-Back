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

    // ??????????????? ????????????
    @Transactional(readOnly = true)
    public PostMainReccomendationDto showPostRecommendation(String board){
        List<Post> postRecommendation = postRepository.findPostByMostViewAndMostLike(board);
        return PostMainReccomendationDto.from(postRecommendation);
    }

    // ??????????????? ??????????????? ?????????
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMain(Long lastPostId, String board, String category) {
        List<Post> postList = postRepository.findCategoryPostOrderByCreated(lastPostId, board, category);
        return PostMainResponseDto.from(postList,board);
    }

    // ??????????????? ??????????????? ?????????
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMainLikeSort(int start, String board, String category) {
        Pageable pageable = PageRequest.of(start, 12);
        List<Post> postList = postRepository.findCategoryPostOrderByLikeDesc(pageable, board, category);
        return PostMainResponseDto.from(postList,board);
    }

    // ?????? ????????? (?????? - ????????????)
    @Transactional
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

    // ????????? ??????
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

    // ????????? ??????
    @Transactional
    public Long updatePost(Long accountId, Long postId, PostUpdate dto, List<MultipartFile> imgFile){
        Post post = postAuthValidation(accountId, postId);
        // ????????? ???????????? ?????????, ????????? ????????? ?????? ????????? ?????????
        if(dto.getDelete_img().size() != 0){
            dto.getDelete_img().forEach(img -> {
                fileProcessService.deleteImage(img.getFilename());
                postImageRepository.deleteByPostImg(img.getFilename());
            });
        }
        setPostImage(imgFile, post);
        if(dto.getHash_tag().size() != 0){
            postTagRepository.deleteAllByPostId(postId);
            setPostTag(dto.getHash_tag(), post);
        }
        post.updatePost(dto);
        return post.getId();
    }

    // ????????? ??????
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
        }else{
            List<com.example.dplus.domain.post.PostComment> postCommentList = postCommentRepository.findAllByPostId(postId);
            postCommentList.forEach((postComment) ->
                    postCommentLikesRepository.deleteAllByPostCommentId(postComment.getId())
            );
        }
        postImageRepository.deleteAllByPostId(postId);
        postBookMarkRepository.deleteAllByPostId(postId);
        postRepository.delete(post);
    }

    // ????????? ??????
    @Transactional(readOnly = true)
    public PostSearchResponseDto findBySearchKeyWord(String keyword, Long lastPostId, String board) {
        List<Post> postLists = postRepository.findPostBySearchKeyWord( keyword,  lastPostId, board);
        return PostSearchResponseDto.from(postLists);
    }

    // ?????? QnA ???????????????
    @Transactional
    public PostAnswerDetailPage detailAnswer(Long accountId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        post.addViewCount();
        // ?????? ???????????? ????????????
        List<PostImage> imgList = postImageRepository.findByPostId(postId);
        // ?????? ?????? ????????????
        List<PostAnswer> postAnswerList = postAnswerRepository.findPostAnswerByPostId(postId);
        // ????????? ???
        List<Long> bookMarkCount = postBookMarkRepository.findBookMarkByPostId(postId);
        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;
        if (accountId != 0) {
            // ?????? ?????????????????? ???????????? ????????? ???????????? ?????????
            isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
            // ?????? ?????????????????? ???????????? ????????? ???????????? ?????????
            isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
            // ?????? ?????????????????? ???????????? ????????? ???????????? ?????????
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, post.getAccount().getId());
            // ?????? ?????????????????? ???????????? ????????? ?????? ???????????? ?????????
            setIsLike(accountId, postAnswerList);
            // ?????? ?????????????????? ???????????? ????????? ?????? ???????????? ?????????
            isFollow(accountId, postAnswerList);
        }
        return PostAnswerDetailPage.from(imgList, postAnswerList,post, isLike, isBookmark, isFollow, (long) bookMarkCount.size());
    }

    // ?????? QnA ??????????????? ??????
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

    // #????????? ????????? ???????????? ????????? (dto -> ?????? ???)
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
    // post ??????, ?????? ?????? ??????
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