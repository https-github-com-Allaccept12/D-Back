package TeamDPlus.code.service.post.comment.like;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikes;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikesRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentLikeService {
    private final PostCommentLikesRepository postCommentLikesRepository;
    private final PostCommentRepository postCommentRepository;

    public void doLike(Account account, Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postCommentLikesRepository.existByAccountIdAndPostCommentId(account.getId(), postCommentId)) {
            throw new BadArgumentsValidException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostCommentLikes postCommentLikes = PostCommentLikes.builder().postComment(postComment).account(account).build();
        postCommentLikesRepository.save(postCommentLikes);
    }

    public void unLike(Account account, Long postCommentId) {
        postCommentLikesRepository.deleteByPostCommentIdAndAccountId(postCommentId,account.getId());
    }
}
