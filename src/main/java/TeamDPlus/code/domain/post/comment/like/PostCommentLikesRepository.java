package TeamDPlus.code.domain.post.comment.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikesRepository extends JpaRepository<PostCommentLikes, Long>, PostCommentLikesRepositoryCustom {
    Long countByPostCommentId(Long postCommentId);
    void deleteByPostCommentIdAndAccountId(Long postCommentId, Long accountId);

    void deleteAllByPostCommentId(Long postCommentId);
}
