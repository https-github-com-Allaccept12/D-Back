package TeamDPlus.code.service.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;

import java.util.List;

public interface ArtworkMainPageService {

    List<ArtWorkResponseDto.ArtworkPageMain> showArtworkMain(Long accountId);
    Long createArtwork(ArtWorkRequestDto.ArtWorkCreate artWorkCreate);
    Long updateArtwork(Long accountId, Long artworkId, ArtWorkRequestDto.ArtWorkUpdate artWorkUpdate);
    void deleteArtwork(Long accountId, Long artworkId);

}
