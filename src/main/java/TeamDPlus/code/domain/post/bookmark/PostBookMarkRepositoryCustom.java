package TeamDPlus.code.domain.post.bookmark;

public interface PostBookMarkRepositoryCustom {
    boolean existByAccountIdAndPostId(Long accountId,Long postId);
}
