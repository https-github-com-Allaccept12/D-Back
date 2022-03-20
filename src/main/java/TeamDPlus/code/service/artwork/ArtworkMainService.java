package TeamDPlus.code.service.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkCreateAndUpdate;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto.ArtworkMain;
import TeamDPlus.code.dto.response.MainResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtworkMain> showArtworkMain(Long accountId, Long artworkId,String category, int sort);

    int createArtwork(Account account, ArtWorkCreateAndUpdate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Account account, Long artworkId, ArtWorkCreateAndUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId);

    ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId);

    List<ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId);




}
