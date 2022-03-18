package TeamDPlus.code.domain.post.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAnswerLikesRepository extends JpaRepository<PostAnswerLikes, Long>, PostAnswerLikesRepositoryCustom {
    void deleteByPostAnswerIdAndAccountId(Long postAnswerId, Long accountId );
}
