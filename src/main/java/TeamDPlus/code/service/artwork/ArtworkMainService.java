package TeamDPlus.code.service.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.domain.Page;

public interface ArtworkMainService {

    Page<ArtWorkResponseDto.ArtworkMain> showArtworkMain(Long accountId,Long artworkId);
    Long createArtwork(Account account,ArtWorkRequestDto.ArtWorkCreateAndUpdate artWorkCreate);

    Long updateArtwork(Account account, Long artworkId, ArtWorkRequestDto.ArtWorkCreateAndUpdate artWorkUpdate);
    void deleteArtwork(Long accountId, Long artworkId);




}
