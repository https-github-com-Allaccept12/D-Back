package TeamDPlus.code.service.artwork;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
import TeamDPlus.code.domain.artwork.comment.ArtWorkCommentRepository;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikesRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkMainServiceImpl implements ArtworkMainService {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkLikesRepository artWorkLikesRepository;
    private final ArtWorkCommentRepository artWorkCommentRepository;
    private final ArtWorkBookMarkRepository artWorkBookMarkRepository;

    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> showArtworkMain(Long accountId,Long lastArtWorkId){
        Pageable pageable = PageRequest.of(0,10);
        Page<ArtWorkResponseDto.ArtworkMain> artWorkList = artWorkRepository.findAllArtWork(lastArtWorkId, pageable);
        setLikeCountAndIsLike(accountId, artWorkList);
        return artWorkList;
    }

    @Transactional(readOnly = true)
    public ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId) {
        final ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new IllegalArgumentException("존재하지않는 게시글 입니다."));
        final List<ArtWorkImage> imgList = artWorkImageRepository.findByArtWorksId(artWorks.getId());
        final List<ArtWorkResponseDto.ArtWorkComment> commentList = artWorkCommentRepository.findArtWorkCommentByArtWorksId(artWorks.getId());
        final boolean isLike = artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        final boolean isBookmark = artWorkBookMarkRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        final int likeCount = artWorkLikesRepository.findArtWorkLikesIdByArtWorksId(artWorkId).size();
        final boolean isFollow = followRepository.existsByFollowerIdAndAndFollowingId(accountId, artWorks.getAccount().getId());
        return ArtWorkResponseDto.ArtWorkDetail.from(imgList,commentList,artWorks,isLike,isBookmark,(long)likeCount,isFollow);
    }


    @Transactional
    public Long createArtwork(Account account, ArtWorkRequestDto.ArtWorkCreateAndUpdate dto) {
        ArtWorks artWorks = ArtWorks.of(account, dto);
        ArtWorks saveArtWork = artWorkRepository.save(artWorks);
        setImgUrl(dto.getImg(), saveArtWork);
        return saveArtWork.getId();
    }

    @Transactional
    public Long updateArtwork(Account account, Long artworkId, ArtWorkRequestDto.ArtWorkCreateAndUpdate dto) {
        ArtWorks findArtWork = artWorkRepository.findById(artworkId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        artWorkImageRepository.deleteAllByArtWorksId(artworkId);
        setImgUrl(dto.getImg(), findArtWork);
        findArtWork.updateArtWork(dto);

        return findArtWork.getId();
    }

    // 작품을 ManyToOne하고 있는 모든 엔티티 삭제
    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId) {
        artWorkLikesRepository.deleteAllByArtWorksId(artworkId);
        artWorkBookMarkRepository.deleteAllByArtWorksId(artworkId);
        artWorkCommentRepository.deleteAllByArtWorksId(artworkId);
        artWorkRepository.delete(artworkValidation(accountId, artworkId));
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


    private ArtWorks artworkValidation(Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new ApiRequestException("권한이 없습니다.");
        }
        return artWorks;
    }

    private void setLikeCountAndIsLike(Long accountId, Page<ArtWorkResponseDto.ArtworkMain> artWorkList) {
        artWorkList.forEach((artWork) -> {
            List<Long> likeCount = artWorkLikesRepository.findArtWorkLikesIdByArtWorksId(artWork.getArtwork_id());
            artWork.setLikeCountAndIsLike((long) likeCount.size(),false);
            if(artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId,artWork.getArtwork_id())) {
                artWork.setLikeCountAndIsLike((long) likeCount.size(),true);
            }
        });
    }
}
