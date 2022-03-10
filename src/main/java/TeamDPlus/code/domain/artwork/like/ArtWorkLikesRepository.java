package TeamDPlus.code.domain.artwork.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkLikesRepository extends JpaRepository<ArtWorkLikes, Long> {
    List<ArtWorkLikes> findLikesListsByArtWorkId(Long artworkId);
}
