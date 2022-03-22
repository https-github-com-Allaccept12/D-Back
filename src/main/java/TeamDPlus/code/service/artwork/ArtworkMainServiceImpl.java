package TeamDPlus.code.service.artwork;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
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
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkCreateAndUpdate;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto.ArtworkMain;
import TeamDPlus.code.dto.response.MainResponseDto;
import TeamDPlus.code.service.file.FileProcessService;
//import jdk.jfr.internal.tool.Main;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));
            List<ArtworkMain> artWorkList = getArtworkList(account.getInterest());
            List<AccountResponseDto.TopArtist> topArtist = getTopArtist(account.getInterest());
            isFollow(accountId,topArtist);
            setIsLike(accountId,artWorkList);
            return MainResponseDto.builder().artwork(artWorkList).top_artist(topArtist).build();
        }
        List<ArtworkMain> artworkList = getArtworkList(null);
        List<AccountResponseDto.TopArtist> topArtist = getTopArtist("");
        return MainResponseDto.builder().artwork(artworkList).top_artist(topArtist).build();
    }
    //둘러보기
    @Transactional(readOnly = true)
    public List<ArtworkMain> showArtworkMain(Long accountId, Long lastArtWorkId,String category,int sortSign){
        Pageable pageable = PageRequest.of(0,10);
        List<ArtworkMain> artWorkList = artWorkRepository.findAllArtWork(lastArtWorkId,category,pageable,sortSign);
        if (accountId != null)
            setIsLike(accountId, artWorkList);
        return artWorkList;
    }

    @Transactional
    public ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId) {
        //작품 게시글 존재여부
        ArtWorks artWorks = artWorkRepository.findById(artWorkId)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        //조회수
        artWorks.addViewCount();
        //작품 좋아요개수와 작품 기본정보 가져오기
        ArtWorkResponseDto.ArtWorkSubDetail artWorksSub = artWorkRepository.findByArtWorkSubDetail(artWorkId);
        //작품 이미지들 가져오기
        List<ArtWorkImage> imgList = artWorkImageRepository.findByArtWorksId(artWorksSub.getArtwork_id());
        //작품 코멘트 가져오기
        List<ArtWorkResponseDto.ArtWorkComment> commentList = artWorkCommentRepository.findArtWorkCommentByArtWorksId(artWorksSub.getArtwork_id());
        //해당 유저의 다른 작품들 가져오기
        Pageable pageable = PageRequest.of(0, 5);
        List<ArtWorkResponseDto.ArtWorkSimilarWork> similarList = artWorkRepository
                .findSimilarArtWork(artWorks.getAccount().getId(),artWorks.getId(),pageable);
        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;
        if (accountId != null) {
            //지금 상세페이지를 보고있는사람이 좋아요를 했는지
            isLike = artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
            //지금 상세페이지를 보고있는사람이 북마크를 했는지
            isBookmark = artWorkBookMarkRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
            //지금 상세페이지를 보고있는사람이 팔로우를 했는지
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, artWorksSub.getAccount_id());
        }
        //상세페이지의 코멘트 개수
        artWorksSub.setComment_count((long) commentList.size());
        return ArtWorkResponseDto.ArtWorkDetail.from(imgList,commentList,similarList,artWorksSub,isLike,isBookmark,isFollow);
    }

    @Transactional
    public int createArtwork(Long accountId, ArtWorkCreateAndUpdate dto, List<MultipartFile> multipartFiles) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));
        if (account.getArtWorkCreateCount() >= 5) {
            throw new ApiRequestException(ErrorCode.DAILY_WRITE_UP_BURN_ERROR);
        }
        ArtWorks artWorks = ArtWorks.of(account, dto);
        ArtWorks saveArtwork = artWorkRepository.save(artWorks);
        s3ImageUpload(multipartFiles, saveArtwork);
        account.upArtworkCountCreate();
        return 5 - account.getArtWorkCreateCount();
    }

    @Transactional
    public Long updateArtwork(Long accountId, Long artworkId, ArtWorkCreateAndUpdate dto, List<MultipartFile> multipartFiles) {
        ArtWorks artWorks = artworkValidation(accountId, artworkId);
        // 작품 이미지가 들어온 경우
        if(multipartFiles!=null){
            updateImg(multipartFiles, artWorks, dto);
            artWorks.updateArtWork(dto);
            return artWorks.getId();
        }
        throw new ApiRequestException(ErrorCode.PHOTO_UPLOAD_ERROR);
    }

    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId) {
        ArtWorks artWorks = artworkValidation(accountId, artworkId);
        List<ArtWorkImage> artWorkImages = artWorkImageRepository.findByArtWorksId(artWorks.getId());
        artWorkImages.forEach((img) -> {
            fileProcessService.deleteImage(img.getArtworkImg());
        });
        artWorkImageRepository.deleteAllByArtWorksId(artworkId);
        artWorkLikesRepository.deleteAllByArtWorksId(artworkId);
        artWorkBookMarkRepository.deleteAllByArtWorksId(artworkId);
        artWorkCommentRepository.deleteAllByArtWorksId(artworkId);
        artWorkRepository.delete(artWorks);
    }

    //작품 검색
    @Transactional(readOnly = true)
    public List<ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        List<ArtworkMain> artWorkList = artWorkRepository.findBySearchKeyWord(keyword, lastArtWorkId, pageable);
        if(accountId != null)
            setIsLike(accountId,artWorkList);
        return artWorkList;
    }

    @Transactional(readOnly = true)
    public List<ArtworkMain> findByFollowerArtWork(Long accountId, String category, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,10);
        List<ArtworkMain> artWorkList = artWorkRepository.findByFollowerArtWork(accountId, category, lastArtWorkId, pageable);
        if(accountId != null)
            setIsLike(accountId,artWorkList);
        return artWorkList;
    }
    private void s3ImageUpload(List<MultipartFile> multipartFiles, ArtWorks saveArtwork) {
        if(multipartFiles != null){
            for (int i = 0; i < multipartFiles.size(); i++) {
                String saveFile = fileProcessService.uploadImage(multipartFiles.get(i));
                ArtWorkImage img = ArtWorkImage.builder().artWorks(saveArtwork).artworkImg(saveFile).thumbnail(false).build();
                if (i == 0) {
                    img.updateThumbnail();
                }
                artWorkImageRepository.save(img);
            }
        }
    }

    private void updateImg( List<MultipartFile> multipartFiles, ArtWorks findArtWork, ArtWorkCreateAndUpdate dto) {
        dto.getImg().forEach((img) -> {
            fileProcessService.deleteImage(img.getImg_url());
            artWorkImageRepository.deleteByArtworkImg(img.getImg_url());
        });
        multipartFiles.forEach((file) -> {
            String imgUrl = fileProcessService.uploadImage(file);
            ArtWorkImage img = ArtWorkImage.builder().artWorks(findArtWork).artworkImg(imgUrl).build();
            artWorkImageRepository.save(img);
        });
    }

    private void isFollow(Long accountId, List<AccountResponseDto.TopArtist> topArtist) {
        topArtist.forEach((artist) -> {
            boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, artist.getAccount_id());
            if (isFollow)
                artist.setIsFollow();
        });
    }

    private List<AccountResponseDto.TopArtist> getTopArtist(String interest) {
        Pageable pageable = PageRequest.of(0,10);
        return accountRepository.findTopArtist(pageable,interest);
    }

    private List<ArtworkMain> getArtworkList(String interest) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findArtWorkByMostViewAndMostLike(interest,pageable);
    }

    private ArtWorks artworkValidation(Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new BadArgumentsValidException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return artWorks;
    }

    private void setIsLike(Long accountId, List<ArtworkMain> artWorkList) {
        artWorkList.forEach((artWork) -> {
            artWork.setLikeCountAndIsLike(artWorkLikesRepository.
                    existByAccountIdAndArtWorkId(accountId, artWork.getArtwork_id()));
        });
    }
}
