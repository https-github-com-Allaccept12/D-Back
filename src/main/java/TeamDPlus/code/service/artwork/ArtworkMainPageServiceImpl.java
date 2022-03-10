package TeamDPlus.code.service.artwork;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikes;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikesRepository;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkMainPageServiceImpl implements ArtworkMainPageService {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkLikesRepository artWorkLikesRepository;

    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkPageMain> showArtworkMain(Long lastArtWorkId){
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findAllArtWork(lastArtWorkId, pageable);
    }

    @Transactional
    public Long createArtwork(Account account, ArtWorkRequestDto.ArtWorkCreate dto) {

        ArtWorks artWorks = ArtWorks.of(account,dto);
        ArtWorks saveArtWork = artWorkRepository.save(artWorks);
        dto.getImg().forEach((img) -> {
            ArtWorkImage artWorkImage = ArtWorkImage.builder().artWorks(saveArtWork).artworkImg(img.getImg_url()).build();
            artWorkImageRepository.save(artWorkImage);
        });
        return saveArtWork.getId();
    }

    public Long updateArtwork(Long accountId, Long artworkId, ArtWorkRequestDto.ArtWorkUpdate artWorkUpdate) {
        return null;
    }

    public void deleteArtwork(Long accountId, Long artworkId) {
//        List<ArtWorkLikes> likesList = artWorkLikesRepository.findLikesListsByArtWorkId(artworkId);
//        artWorkLikesRepository.deleteAll(likesList);
//        artWorkRepository.delete(artworkValidation(accountId, artworkId));
    }

    private ArtWorks artworkValidation(Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return artWorks;
    }
}
