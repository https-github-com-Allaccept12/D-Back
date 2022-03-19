package TeamDPlus.code.domain.post.comment.like;

public interface PostCommentLikesRepositoryCustom {
    boolean existByAccountIdAndPostCommentId(Long accountId,Long postCommentId);
}
