package TeamDPlus.code.service.post.like;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    @Transactional
    public void doLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postLikesRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostLikes postLikes = PostLikes.builder().post(post).account(account).build();
        postLikesRepository.save(postLikes);
    }

    @Transactional
    public void unLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (!postLikesRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postLikesRepository.deleteByPostIdAndAccountId(post.getId(),account.getId());
    }
}
