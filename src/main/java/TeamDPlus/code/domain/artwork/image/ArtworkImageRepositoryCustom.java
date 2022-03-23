package TeamDPlus.code.domain.artwork.image;

import TeamDPlus.code.dto.common.CommonDto;

import java.util.List;

public interface ArtworkImageRepositoryCustom {

    List<CommonDto.ImgUrlDto> findArtWorkImageByTopView(Long accountId);

    ArtWorkImage findByThumbnail(Long artWorksId);
}
