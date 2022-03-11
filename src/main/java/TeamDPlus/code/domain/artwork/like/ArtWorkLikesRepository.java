package TeamDPlus.code.domain.artwork.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkLikesRepository extends JpaRepository<ArtWorkLikes, Long>,ArtWorkLikesRepositoryCustom {

    List<Long> findArtWorkLikesIdByArtWorksId(Long artWorkId);

    void deleteAllByArtWorksId(Long artWorkId);

    void deleteByArtWorksIdAndAccountId(Long artWorkId,Long accountId);

}
