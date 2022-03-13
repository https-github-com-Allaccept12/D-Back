package TeamDPlus.code.service.artwork;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtworkMainServiceImpl implements ArtworkMainService {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkLikesRepository artWorkLikesRepository;
    private final ArtWorkCommentRepository artWorkCommentRepository;
    private final ArtWorkBookMarkRepository artWorkBookMarkRepository;
    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> showArtworkMain(Long accountId,Long lastArtWorkId){
        Pageable pageable = PageRequest.of(0,10);
        Page<ArtWorkResponseDto.ArtworkMain> artWorkList = artWorkRepository.findAllArtWork(lastArtWorkId, pageable);
        setLikeCountAndIsLike(accountId, artWorkList);
        return artWorkList;
    }

    @Transactional(readOnly = true)
    public ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId) {
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new IllegalArgumentException("존재하지않는 게시글 입니다."));
        Long likeCount = artWorkLikesRepository.countArtWorkLikesByArtWorksId(artWorkId);

        List<ArtWorkImage> imgList = artWorkImageRepository.findByArtWorksId(artWorks.getId());
        List<ArtWorkResponseDto.ArtWorkComment> commentList = artWorkCommentRepository.findArtWorkCommentByArtWorksId(artWorks.getId());

        final Pageable pageable = PageRequest.of(0, 5);
        Page<ArtWorkResponseDto.ArtWorkSimilarWork> similarList = artWorkRepository.findSimilarArtWork(accountId,pageable);

        boolean isLike = artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        boolean isBookmark = artWorkBookMarkRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        boolean isFollow = followRepository.existsByFollowerIdAndAndFollowingId(accountId, artWorks.getAccount().getId());

        //return ArtWorkResponseDto.ArtWorkDetail.from(imgList,commentList,artWorks,isLike,isBookmark,likeCount,isFollow,similarList);
        return null;
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

    //비회원 일경우 모든 작품 카테고리에서 탑10
    //회원 일경우 관심사카테고리중에서 탑10
    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> mostPopularArtWork(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        Pageable pageable = PageRequest.of(0,10);
        Page<ArtWorkResponseDto.ArtworkMain> artWorkList = artWorkRepository.findArtWorkByMostViewAndMostLike(account.get().getInterest(),pageable);
        setLikeCountAndIsLike(accountId, artWorkList);
        return null;
    }
    //작품 검색
    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findBySearchKeyWord(keyword, lastArtWorkId, pageable);
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
            Long likeCount = artWorkLikesRepository.countArtWorkLikesByArtWorksId(artWork.getArtwork_id());
            artWork.setLikeCountAndIsLike(likeCount,false);
            if(artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId,artWork.getArtwork_id())) {
                artWork.setLikeCountAndIsLike(likeCount,true);
            }
        });
    }
}
