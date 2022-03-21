package TeamDPlus.code.domain.artwork.image;

import TeamDPlus.code.domain.artwork.like.ArtWorkLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkImageRepository extends JpaRepository<ArtWorkImage, Long>,ArtworkImageRepositoryCustom {

    List<ArtWorkImage> findByArtWorksId(Long artWorkId);

    void deleteAllByArtWorksId(Long artWorkId);

    void deleteByArtworkImg(String artWorkImg);
}
