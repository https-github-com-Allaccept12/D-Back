package TeamDPlus.code.service.artwork;

import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto.ArtworkMain;
import TeamDPlus.code.dto.response.MainResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtworkMain> showArtworkMain(Long accountId, Long artworkId,String category, int sort);

    int createArtwork(Long accountId, ArtWorkCreate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Long account, Long artworkId, ArtWorkUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId);

    ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId);

    List<ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId);




}
