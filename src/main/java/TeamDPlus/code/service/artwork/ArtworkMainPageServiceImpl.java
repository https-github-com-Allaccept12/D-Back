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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkMainPageServiceImpl implements ArtworkMainPageService {

    private AccountResponseDto accountResponseDto;
    private ArtWorkRepository artWorkRepository;
    private ArtWorkLikesRepository artWorkLikesRepository;

    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtworkPageMain> showArtworkMain(String accountId){
//        List<ArtWorkResponseDto.ArtworkPageMain> artworkPageMains = new ArrayList<>();
//        List<ArtWorks> artworks = artWorkRepository.findAll();
//        List<ArtWorkLikes> artWorkLikes = artWorkLikesRepository.findLikesListsByArtWorkId()
//        if (account_id != null){
//            // 좋아요
//
//
//        } else {
//            // 그냥 전체 게시물
//
//        }
//
        return
    }

    @Override
    @Transactional
    public Long createArtwork(Account account, ArtWorkRequestDto.ArtWorkCreate dto) {

        ArtWorkImage artWorkImage = ArtWorkImageRepository.save(artwork)

        ArtWorks artWorks = ArtWorks.builder()
                .account(account)
                .category(dto.getCategory())
                .content(dto.getContent())
                .scope(dto.getScope())
                .title(dto.getTitle())
                .workStart(dto.getWork_start())
                .workEnd(dto.getWork_end())
                .build();


//        Long createdArtwork = ArtWorkRepository.save(ArtWorkRequestDto.toEntity(dto, account)).getId();

        return createdArtwork;
    }

    @Override
    public String updateArtwork(Long accountId, Long artworkId, ArtWorkRequestDto.ArtWorkUpdate artWorkUpdate) {
        return null;
    }

    @Override
    public void deleteArtwork(Long accountId, Long artworkId) {
        List<ArtWorkLikes> likesList = artWorkLikesRepository.findLikesListsByArtWorkId(artworkId);
        artWorkLikesRepository.deleteAll(likesList);
        artWorkRepository.delete(artworkValidation(accountId, artworkId));
    }

    private ArtWorks artworkValidation (Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return artWorks;
    }
}
