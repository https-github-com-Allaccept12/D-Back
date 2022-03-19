package TeamDPlus.code.domain.post.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookMarkRepository extends JpaRepository<PostBookMark, Long>, PostBookMarkRepositoryCustom {
    Long countByPostId(Long postId);

    void deleteAllByPostId(Long postId);

    void deleteByPostIdAndAccountId(Long postId, Long accountId);
}
