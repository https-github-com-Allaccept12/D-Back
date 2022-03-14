package TeamDPlus.code.service.post.like;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    public void doLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
        if (postLikesRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException("이미 좋아요한 게시글 입니다.");
        }
        PostLikes postLikes = PostLikes.builder().post(post).account(account).build();
        postLikesRepository.save(postLikes);
    }

    public void unLike(Account account, Long postId) {
        postLikesRepository.deleteByPostByAndAccountId(postId,account.getId());
    }
}
