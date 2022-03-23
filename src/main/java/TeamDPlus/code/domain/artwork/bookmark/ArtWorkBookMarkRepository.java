package TeamDPlus.code.domain.artwork.bookmark;

import TeamDPlus.code.domain.artwork.comment.ArtWorkComment;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkBookMarkRepository extends JpaRepository<ArtWorkBookMark,Long> ,ArtWorkBookMarkRepositoryCustom{

    List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long accountId);

    List<ArtWorkBookMark> findArtWorkBookMarkByArtWorksId(Long artWorkId);

    void deleteAllByArtWorksId(Long artWorkId);

    void deleteByArtWorksIdAndAccountId(Long artWorkId,Long accountId);
}
