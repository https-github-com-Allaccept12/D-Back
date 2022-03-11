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
import TeamDPlus.code.dto.common.CommonDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkMainPageServiceImpl implements ArtworkMainPageService {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkLikesRepository artWorkLikesRepository;

    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkPageMain> showArtworkMain(Long accountId,Long lastArtWorkId){
        Pageable pageable = PageRequest.of(0,10);
        Page<ArtWorkResponseDto.ArtworkPageMain> artWorkList = artWorkRepository.findAllArtWork(lastArtWorkId, pageable);
        setLikeCountAndIsLike(accountId, artWorkList);
        return artWorkList;
    }

    @Transactional
    public Long createArtwork(Account account, ArtWorkRequestDto.ArtWorkCreate dto) {
        ArtWorks artWorks = ArtWorks.of(account,dto);
        ArtWorks saveArtWork = artWorkRepository.save(artWorks);
        setImgUrl(dto.getImg(), saveArtWork);
        return saveArtWork.getId();
    }

    @Transactional
    public Long updateArtwork(Account account, Long artworkId, ArtWorkRequestDto.ArtWorkUpdate dto) {
        ArtWorks findArtWork = artWorkRepository.findById(artworkId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        artWorkImageRepository.deleteAllByArtWorksId(artworkId);
        setImgUrl(dto.getImg(), findArtWork);
        findArtWork.updateArtWork(dto);

        return findArtWork.getId();
    }

    private void setImgUrl(List<CommonDto.ImgUrlDto> dto, ArtWorks artWork) {
        dto.forEach((img) -> {
            ArtWorkImage artWorkImage = ArtWorkImage.builder()
                    .artWorks(artWork)
                    .artworkImg(img.getImg_url())
                    .thumbnail(img.isThumbnail())
                    .build();
            artWorkImageRepository.save(artWorkImage);
        });
    }

    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId) {
        List<Long> likesList = artWorkLikesRepository.findArtWorkLikesIdByArtWorksId(artworkId);
        artWorkLikesRepository.deleteAllById(likesList);
        artWorkRepository.delete(artworkValidation(accountId, artworkId));
    }

    private ArtWorks artworkValidation(Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return artWorks;
    }

    private void setLikeCountAndIsLike(Long accountId, Page<ArtWorkResponseDto.ArtworkPageMain> artWorkList) {
        artWorkList.forEach((artWork) -> {
            List<Long> likeCount = artWorkLikesRepository.findArtWorkLikesIdByArtWorksId(artWork.getArtwork_id());
            artWork.setLikeCountAndIsLike((long) likeCount.size(),false);
            if(artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId,artWork.getArtwork_id())) {
                artWork.setLikeCountAndIsLike((long) likeCount.size(),true);
            }
        });
    }
}
