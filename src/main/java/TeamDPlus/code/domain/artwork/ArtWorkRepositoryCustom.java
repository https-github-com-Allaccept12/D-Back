package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;

import java.util.List;
import java.util.Optional;

public interface ArtWorkRepositoryCustom {

    List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long accountId);

    List<ArtWorkResponseDto.ArtworkPageMain> findByArtWorkOrderByCreatedDesc();
}
