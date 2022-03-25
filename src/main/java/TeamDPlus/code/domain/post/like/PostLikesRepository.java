package TeamDPlus.code.domain.post.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, PostLikesRepositoryCustom {
    Long countByPostId(Long postId);
    void deleteAllByPostId(Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
