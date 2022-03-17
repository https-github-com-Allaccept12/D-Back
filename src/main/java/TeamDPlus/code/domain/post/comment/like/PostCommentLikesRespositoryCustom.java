package TeamDPlus.code.domain.post.comment.like;

public interface PostCommentLikesRespositoryCustom {
    boolean existByAccountIdAndPostCommentId(Long accountId,Long postCommentId);
}
