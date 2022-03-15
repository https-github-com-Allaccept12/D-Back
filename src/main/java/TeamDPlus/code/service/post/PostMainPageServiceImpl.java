package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
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

    // 전체 페이지
    @Transactional(readOnly = true)
    public Page<PostResponseDto.PostPageMain> showPostMain(Long accountId, Long lastPostId) {
        Pageable pageable = PageRequest.of(0,12);
        Page<PostResponseDto.PostPageMain> postList = postRepository.findAllPost(lastPostId, pageable);
        setCountList(postList);
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

}
