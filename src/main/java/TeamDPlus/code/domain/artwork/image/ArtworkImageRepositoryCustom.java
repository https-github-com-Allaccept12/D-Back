package TeamDPlus.code.domain.artwork.image;

import TeamDPlus.code.dto.common.CommonDto;

import java.util.List;

public interface ArtworkImageRepositoryCustom {


    List<String> findByAllImageUrl(Long artworkId);
}
