package com.example.dplus.service.post;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.follow.FollowRepository;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.domain.post.PostBookMark;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.repository.post.comment.PostCommentLikesRepository;
import com.example.dplus.domain.post.PostImage;
import com.example.dplus.repository.post.image.PostImageRepository;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import com.example.dplus.repository.post.like.PostLikesRepository;
import com.example.dplus.domain.post.PostTag;
import com.example.dplus.repository.post.tag.PostTagRepository;
import com.example.dplus.dto.common.CommonDto;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.dto.response.PostMainResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import com.example.dplus.dto.response.PostResponseDto.PostAnswerDetailPage;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


    // 메인 페이지 (최신순)
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostMain(Long accountId, Long lastPostId, String board, String category) {

        // 회원이면
        if (accountId != 0){
            // 메인 페이지 전체 피드
            Pageable pageable = PageRequest.of(0,12);
            List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByCreatedDesc(lastPostId, pageable, board, category);
            setCountList(postList);

            // 메인페이지 추천피드
            List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
            setCountList(postRecommendation);
            for(int i = 0; i< postRecommendation.size(); i++) {
                List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
                if (tagDtos.size()>0){
                    postList.get(i).setHash_tag(tagDtos);
                }
            }
            return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
        }

        // 메인 페이지 전체 피드
        Pageable pageable = PageRequest.of(0,12);

        List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByCreatedDesc(lastPostId, pageable, board, category);
        setCountList(postList);
        for(int i = 0; i< postList.size(); i++) {
            List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
            postList.get(i).setHash_tag(tagDtos);
        }

        // 메인페이지 추천피드
        List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
        setCountList(postRecommendation);
        for(int i = 0; i< postRecommendation.size(); i++) {
            List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
            if (tagDtos.size()>0){
                postList.get(i).setHash_tag(tagDtos);
            }
        }

        return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
    }

    // 메인 페이지 (좋아요 순)
    @Transactional(readOnly = true)
    public PostMainResponseDto showPostLikeMain(Long accountId, String board, String category,int start) {

        // 회원이면
        if (accountId != 0){
            // 메인 페이지 전체 피드
            Pageable pageable = PageRequest.of(start,12);
            List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByLikes(pageable, board, category);
            // 메인페이지 추천피드
            List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
            setCountList(postRecommendation);
            for(int i = 0; i< postRecommendation.size(); i++) {
                List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
                if (tagDtos.size()>0){
                    postList.get(i).setHash_tag(tagDtos);
                }
            }
            return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
        }

        // 메인 페이지 전체 피드
        Pageable pageable = PageRequest.of(start,12);

        List<PostResponseDto.PostPageMain> postList = postRepository.findAllPostOrderByLikes(pageable, board, category);
        setCountList(postList);
        for(int i = 0; i< postList.size(); i++) {
            List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
            postList.get(i).setHash_tag(tagDtos);
        }

        // 메인페이지 추천피드
        List<PostResponseDto.PostPageMain> postRecommendation = postRepository.findPostByMostViewAndMostLike();
        setCountList(postRecommendation);
        for(int i = 0; i< postRecommendation.size(); i++) {
            List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postList.get(i).getPost_id());
            if (tagDtos.size()>0){
                postList.get(i).setHash_tag(tagDtos);
            }
        }

        return PostMainResponseDto.builder().postMainPage(postList).postRecommendationFeed(postRecommendation).build();
    }

    // 상세 게시글 (디플 - 꿀팁)
    @Transactional
    public PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        post.addViewCount();
        PostResponseDto.PostSubDetail postSubDetail = postRepository.findByPostSubDetail(postId);
        List<PostImage> postImageList = postImageRepository.findByPostId(postId);
        List<PostBookMark> bookmarks = postBookMarkRepository.findByPostId(postId);
        List<PostResponseDto.PostComment> postComments = postCommentRepository.findPostCommentByPostId(postId);

        // 코멘트가 있으면 코멘트 좋아요 여부 체크
        if(postComments.size()>0 && accountId != 0){
            postComments.forEach((comment) -> {
                Boolean commentLike = postCommentLikesRepository.existByAccountIdAndPostCommentId(accountId, comment.getComment_id());
                comment.setIs_comment_like(commentLike);
            });
        }
        Long comment_count = (long) postComments.size();
        Long bookmark_count = (long) bookmarks.size();

        List<PostTag> postTags = postTagRepository.findPostTagsByPostId(postId);

        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;

        // 게시글의 좋아요, 북마크, 팔로잉 여부 체크
        if(accountId != 0){
            isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
            isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, postSubDetail.getAccount_id());
        }

        return PostResponseDto.PostDetailPage.from(postImageList, postComments,
                postTags, postSubDetail, isLike, isBookmark, isFollow, comment_count, bookmark_count);
    }

    // 게시글 작성
    @Transactional
    public int createPost(Long accountId, PostRequestDto.PostCreate dto, List<MultipartFile> imgFile) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        if (account.getPostCreateCount() >= 5) {
            throw new ErrorCustomException(ErrorCode.DAILY_POST_WRITE_UP_BURN_ERROR);
        }

        Post post = Post.of(account, dto);
        Post savedPost = postRepository.save(post);
        if(imgFile!=null){
            imgFile.forEach((file) -> {
                String img_url = fileProcessService.uploadImage(file);
                PostImage postImage = PostImage.builder().post(post).postImg(img_url).build();
                postImageRepository.save(postImage);
            });
        }

        setPostTag(dto.getHashTag(), savedPost);
        account.upPostCountCreate();
        return 5 - account.getPostCreateCount();
    }

    // 게시물 수정
    @Transactional
    public Long updatePost(Account account, Long postId, PostRequestDto.PostUpdate dto, List<MultipartFile> imgFile){
        Post post = postAuthValidation(account.getId(), postId);

        // 삭제할 이미지가 있다면, 이미지 주소를 직접 하나씩 지운다
        if(dto.getDelete_img()!=null){
            for(int i = 0; i < dto.getDelete_img().size(); i++){
                String deleteImage = dto.getDelete_img().get(i).getFilename();
                // 서버 파일 삭제
                fileProcessService.deleteImage(deleteImage);
                // db 삭제
                postImageRepository.deleteByPostImg(deleteImage);
            }
        }
        // 업로드할 이미지가 있다면, 이미지를 업로드한다.
        if(imgFile!=null){
            imgFile.forEach((file) -> {
                String img_url = fileProcessService.uploadImage(file);
                PostImage postImage = PostImage.builder().post(post).postImg(img_url).build();
                postImageRepository.save(postImage);
            });
        }

        // 게시글을 업데이트 한다
        post.updatePost(dto);

        // 태그도 지우고 다시 세팅
        if(dto.getHashTag()!=null){
            postTagRepository.deleteAllByPostId(postId);
            setPostTag(dto.getHashTag(), post);
        }
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
    public List<PostResponseDto.PostSearchMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId, String board) {
        Pageable pageable = PageRequest.of(0,12);
        List<PostResponseDto.PostSearchMain> postSearched = postRepository.findPostBySearchKeyWord(keyword,lastArtWorkId,pageable, board);
        for(int i = 0; i<postSearched.size();i++){
            List<PostTag> tagDtos = postTagRepository.findPostTagsByPostId(postSearched.get(i).getPost_id());
            postSearched.get(i).setHash_tag(tagDtos);
        }
        setCountSearchPost(postSearched);
        return postSearched;
    }

    // 디모 QnA 상세페이지
    @Transactional
    public PostAnswerDetailPage detailAnswer(Long accountId, Long postId) {
        // 작품 게시글 존재여부
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        // 조회수 + 1
        post.addViewCount();
        // QnA 좋아요 개수와 QnA 기본정보 가져오기
        PostResponseDto.PostAnswerSubDetail postAnswerSubDetail = postRepository.findByPostAnswerSubDetail(postId);
        // 작품 이미지들 가져오기
        List<PostImage> imgList = postImageRepository.findByPostId(postId);
        // 질문 답변 가져오기
        List<PostResponseDto.PostAnswer> postAnswerList = postAnswerRepository.findPostAnswerByPostId(postId);
        // 태그 리스트 가져오기
        List<PostTag> postTagList = postTagRepository.findPostTagsByPostId(postId);
        // 북마크 수
        Long bookMarkCount = postBookMarkRepository.countByPostId(postId);

        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;

        if (accountId != 0) {
            // 지금 상세페이지를 보고있는 사람이 좋아요를 했는지
            isLike = postLikesRepository.existByAccountIdAndPostId(accountId, postId);
            // 지금 상세페이지를 보고있는 사람이 북마크를 했는지
            isBookmark = postBookMarkRepository.existByAccountIdAndPostId(accountId, postId);
            // 지금 상세페이지를 보고있는 사람이 팔로우를 했는지
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, postAnswerSubDetail.getAccount_id());
            // 지금 상세페이지를 보고있는 사람이 답글 좋아요를 했는지
            setIsLike(accountId, postAnswerList);
            // 지금 상세페이지를 보고있는 사람이 답글 팔로우를 했는지
            isFollow(accountId, postAnswerList);
        }

        //상세페이지의 답글 개수
        postAnswerSubDetail.setAnswer_count((long) postAnswerList.size());
        return PostAnswerDetailPage.from(imgList, postAnswerList, postTagList, postAnswerSubDetail, isLike, isBookmark, isFollow, bookMarkCount);
    }

    // 디모 QnA 유사한질문 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto.PostSimilarQuestion> similarQuestion(String category, Long accountId, Long postId) {
        List<PostResponseDto.PostSimilarQuestion> postSimilarList = postRepository.findByCategory(category, "QNA", postId);
        if (accountId != null) {
            postSimilarList.forEach((postSimilar) -> {
                // 좋아요 여부
                postSimilar.setLikeCountAndIsLike(false);
                if (postLikesRepository.existByAccountIdAndPostId(accountId, postSimilar.getPost_id())) {
                    postSimilar.setLikeCountAndIsLike(true);
                }

                // 북마크 여부
                postSimilar.setIsBookmark(false);
                if (postBookMarkRepository.existByAccountIdAndPostId(accountId, postSimilar.getPost_id())) {
                    postSimilar.setIsBookmark(true);
                }

                // 질문 답변 가져오기
                Long answerCount = postAnswerRepository.countByPostId(postSimilar.getPost_id());
                postSimilar.setAnswer_count(answerCount);

                Long bookMarkCount = postBookMarkRepository.countByPostId(postSimilar.getPost_id());
                postSimilar.setBookmark_count(bookMarkCount);

                // 태그 리스트 가져오기
                List<PostTag> postTagList = postTagRepository.findPostTagsByPostId(postSimilar.getPost_id());
                postSimilar.setHash_tag(postTagList);

            });
            return postSimilarList;
        }

        postSimilarList.forEach((postSimilar) -> {
            // 질문 답변 가져오기
            Long answerCount = postAnswerRepository.countByPostId(postSimilar.getPost_id());
            postSimilar.setAnswer_count(answerCount);

            Long bookMarkCount = postBookMarkRepository.countByPostId(postSimilar.getPost_id());
            postSimilar.setBookmark_count(bookMarkCount);

            // 태그 리스트 가져오기
            List<PostTag> postTagList = postTagRepository.findPostTagsByPostId(postSimilar.getPost_id());
            postSimilar.setHash_tag(postTagList);

        });
        return postSimilarList;
    }

        // #단위로 끊어서 해쉬태그 들어옴 (dto -> 받을 때)
        private void setPostTag(List<CommonDto.PostTagDto> dto, Post post){
            dto.forEach((tag) -> {
                PostTag postTag = PostTag.builder()
                        .post(post)
                        .hashTag(tag.getTag())
                        .build();
                postTagRepository.save(postTag);
            });
        }
    private void setCountList(List<PostResponseDto.PostPageMain> postList){
        postList.forEach((post) -> {
            Long comment_count = postCommentRepository.countByPostId(post.getPost_id());
            post.setCountList(comment_count);
        });
    }

    // post 수정, 삭제 권한 확인
    private Post postAuthValidation(Long accountId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if(!post.getAccount().getId().equals(accountId)){
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return post;
    }
    private void isFollow(Long accountId, List<PostResponseDto.PostAnswer> postAnswerList) {
        postAnswerList.forEach((postAnswer) -> {
            postAnswer.setIsFollow(false);
            boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, postAnswer.getAccount_id());
            if (isFollow)
                postAnswer.setIsFollow(true);
        });
    }

    private void setIsLike(Long accountId, List<PostResponseDto.PostAnswer> postAnswerList) {
        postAnswerList.forEach((postAnswer) -> {
            postAnswer.setLikeCountAndIsLike(false);
            if(postAnswerLikesRepository.existByAccountIdAndPostAnswerId(accountId, postAnswer.getAnswer_id())) {
                postAnswer.setLikeCountAndIsLike(true);
            }
        });
    }

    private void setCountSearchPost(List<PostResponseDto.PostSearchMain> searchPost){
        searchPost.forEach((post) -> {
            Long comment_count = postCommentRepository.countByPostId(post.getPost_id());
            Long like_count = postLikesRepository.countByPostId(post.getPost_id());
            post.setCountList(comment_count,like_count);
        });
    }

}