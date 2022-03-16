package TeamDPlus.code.domain.post.comment.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikesRepository extends JpaRepository<PostCommentLikes, Long>, PostCommentLikesRespositoryCustom {
    Long countByPostCommentId(Long postCommentId);
    void deletePostCommentLikesByPostCommentId(Long postCommentId, Long accountId );
}
