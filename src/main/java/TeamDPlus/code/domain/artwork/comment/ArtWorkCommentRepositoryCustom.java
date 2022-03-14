package TeamDPlus.code.domain.artwork.comment;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;

import java.util.List;

public interface ArtWorkCommentRepositoryCustom {

    List<ArtWorkResponseDto.ArtWorkComment> findArtWorkCommentByArtWorksId(Long artWorkId);
}
