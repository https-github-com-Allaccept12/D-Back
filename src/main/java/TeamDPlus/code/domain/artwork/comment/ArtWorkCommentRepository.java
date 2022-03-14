package TeamDPlus.code.domain.artwork.comment;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArtWorkCommentRepository extends JpaRepository<ArtWorkComment, Long>,ArtWorkCommentRepositoryCustom {

    void deleteAllByArtWorksId(Long artWorkId);

}
