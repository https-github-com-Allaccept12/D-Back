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
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.MainResponseDto;
import TeamDPlus.code.service.file.FileProcessService;
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
    private final AccountRepository accountRepository;
    private final FileProcessService fileProcessService;

    //비회원 일경우 모든작품 카테고리에서 탑10
    //회원 일경우 관심사 카테고리중에서 탑10
    @Transactional(readOnly = true)
    public MainResponseDto mostPopularArtWork(Long accountId) {
        //회원인지 비회원인지
        if (accountId != null) {
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            Page<ArtWorkResponseDto.ArtworkMain> artWorkList = getArtworkList(account.getInterest());
            List<AccountResponseDto.TopArtist> topArtist = getTopArtist();
            setIsLike(accountId, artWorkList);
            return MainResponseDto.builder().artwork(artWorkList).top_artist(topArtist).build();

        }
        Page<ArtWorkResponseDto.ArtworkMain> artworkList = getArtworkList(null);
        List<AccountResponseDto.TopArtist> topArtist = getTopArtist();
        return MainResponseDto.builder().artwork(artworkList).top_artist(topArtist).build();

    }

    private List<AccountResponseDto.TopArtist> getTopArtist() {
        return accountRepository.findTopArtist();
    }

    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> showArtworkMain(Long accountId,Long lastArtWorkId){
        Pageable pageable = PageRequest.of(0,10);
        Page<ArtWorkResponseDto.ArtworkMain> artWorkList = artWorkRepository.findAllArtWork(lastArtWorkId, pageable);
        setIsLike(accountId, artWorkList);
        return artWorkList;
    }

    @Transactional(readOnly = true)
    public ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId) {
        //작품 게시글 존재여부
        ArtWorks artWorks = artworkValidation(accountId, artWorkId);
        //조회수
        artWorks.addViewCount();
        //작품 좋아요개수와 작품 기본정보 가져오기
        ArtWorkResponseDto.ArtWorkSubDetail artWorksSub = artWorkRepository.findByArtWorkSubDetail(accountId, artWorkId);
        //작품 이미지들 가져오기
        List<ArtWorkImage> imgList = artWorkImageRepository.findByArtWorksId(artWorksSub.getArtwork_id());
        //작품 코멘트 가져오기
        List<ArtWorkResponseDto.ArtWorkComment> commentList = artWorkCommentRepository.findArtWorkCommentByArtWorksId(artWorksSub.getArtwork_id());
        //해당 유저의 다른 작품들 가져오기
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArtWorkResponseDto.ArtWorkSimilarWork> similarList = artWorkRepository.findSimilarArtWork(accountId,pageable);

        //지금 상세페이지를 보고있는사람이 좋아요를 했는지
        boolean isLike = artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        //지금 상세페이지를 보고있는사람이 북마크를 했는지
        boolean isBookmark = artWorkBookMarkRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
        //지금 상세페이지를 보고있는사람이 팔로우를 했는지
        boolean isFollow = followRepository.existsByFollowerIdAndAndFollowingId(accountId, artWorksSub.getAccount_id());
        //상세페이지의 코멘트 개수
        artWorksSub.setComment_count((long) commentList.size());
        return ArtWorkResponseDto.ArtWorkDetail.from(imgList,commentList,similarList,artWorksSub,isLike,isBookmark,isFollow);
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
        //s3에서도 삭제
        setImgUrl(dto.getImg(), findArtWork);
        findArtWork.updateArtWork(dto);
        return findArtWork.getId();
    }

    // 작품을 ManyToOne하고 있는 모든 엔티티 삭제 - s3에서도 삭제
    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId) {
        artWorkLikesRepository.deleteAllByArtWorksId(artworkId);
        artWorkBookMarkRepository.deleteAllByArtWorksId(artworkId);
        artWorkCommentRepository.deleteAllByArtWorksId(artworkId);
        artWorkRepository.delete(artworkValidation(accountId, artworkId));
    }


    //작품 검색
    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findBySearchKeyWord(keyword, lastArtWorkId, pageable);
    }

    private Page<ArtWorkResponseDto.ArtworkMain> getArtworkList(String interest) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findArtWorkByMostViewAndMostLike(interest,pageable);
    }

    //이미지 s3에서도 업로드
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

    private void setIsLike(Long accountId, Page<ArtWorkResponseDto.ArtworkMain> artWorkList) {
        artWorkList.forEach((artWork) -> {
            artWork.setLikeCountAndIsLike(false);
            if(artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId,artWork.getArtwork_id())) {
                artWork.setLikeCountAndIsLike(true);
            }
        });
    }
}
