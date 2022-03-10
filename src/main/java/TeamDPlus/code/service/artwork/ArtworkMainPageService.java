package TeamDPlus.code.service.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArtworkMainPageService {

    Page<ArtWorkResponseDto.ArtworkPageMain> showArtworkMain(Long accountId);
    Long createArtwork(Account account,ArtWorkRequestDto.ArtWorkCreate artWorkCreate);

    Long updateArtwork(Long accountId, Long artworkId, ArtWorkRequestDto.ArtWorkUpdate artWorkUpdate);
    void deleteArtwork(Long accountId, Long artworkId);




}
