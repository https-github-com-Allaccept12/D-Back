package TeamDPlus.code.domain.post.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);
    void deleteByPostByAndAccountId(Long postId, Long accountId);
}
