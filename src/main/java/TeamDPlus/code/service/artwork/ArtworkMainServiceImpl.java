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

import java.util.ArrayList;
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
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            List<ArtworkMain> artWorkList = getArtworkList(account.getInterest());
            List<AccountResponseDto.TopArtist> topArtist = getTopArtist();
            isFollow(accountId,topArtist);
            setIsLike(accountId,artWorkList);
            return MainResponseDto.builder().artwork(artWorkList).top_artist(topArtist).build();
        }
        List<ArtworkMain> artworkList = getArtworkList(null);
        List<AccountResponseDto.TopArtist> topArtist = getTopArtist();
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
                .orElseThrow(() -> new ApiRequestException("해당 게시글은 존재하지 않습니다."));
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
    public int createArtwork(Account account, ArtWorkCreateAndUpdate dto, List<MultipartFile> multipartFiles) {
        if (account.getArtWorkCreateCount() >= 5) {
            throw new ApiRequestException("일일 작성 가능한 게시글분을 다 사용하셨습니다.");
        }
        // 아트웍 저장
        ArtWorks artWorks = ArtWorks.of(account, dto);
        ArtWorks saveArtwork = artWorkRepository.save(artWorks);

        // 작품 이미지가 들어온 경우
        if(multipartFiles != null){
            // 데이터 저장
            multipartFiles.forEach((file) -> {
                String saveFile = fileProcessService.uploadImage(file);
                ArtWorkImage img = ArtWorkImage.builder().artWorks(saveArtwork).artworkImg(saveFile).build();
                artWorkImageRepository.save(img);
            });
        }
        account.upArtworkCountCreate();
        return 5 - account.getArtWorkCreateCount();
    }


    @Transactional
    public Long updateArtwork(Account account, Long artworkId, ArtWorkCreateAndUpdate dto, List<MultipartFile> multipartFiles) {
        ArtWorks findArtWork = artWorkRepository.findById(artworkId).orElseThrow(() -> new ApiRequestException("게시글이 존재하지 않습니다."));
        List<ArtWorkImage> artWorkImages = artWorkImageRepository.findByArtWorksId(findArtWork.getId());
        // 작품 이미지가 들어온 경우
        if(multipartFiles!=null){
            artWorkImages.forEach((img) -> {
                // S3의 이미지 하나씩 삭제
                fileProcessService.deleteImage(img.getArtworkImg());
            });
            // db 삭제
            artWorkImageRepository.deleteAllByArtWorksId(artworkId);

            // 데이터 재 저장
            multipartFiles.forEach((file) -> {
                String s = fileProcessService.uploadImage(file);
                ArtWorkImage img = ArtWorkImage.builder().artWorks(findArtWork).artworkImg(s).build();
                artWorkImageRepository.save(img);
            });
        }
        setImgUrl(dto.getImg(), findArtWork);
        findArtWork.updateArtWork(dto);
        return findArtWork.getId();
    }

    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId) {
        ArtWorks artWorks = artworkValidation(accountId, artworkId);
        List<ArtWorkImage> artWorkImages = artWorkImageRepository.findByArtWorksId(artWorks.getId());
        artWorkImages.forEach((img) -> {
            // S3의 이미지 하나씩 삭제
            fileProcessService.deleteImage(img.getArtworkImg());
        });
        // db 삭제
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

    @Override
    public List<ArtworkMain> findByFollowerArtWork(Long accountId, String category, Long lastArtWorkId) {
        Pageable pageable = PageRequest.of(0,10);
        List<ArtworkMain> artWorkList = artWorkRepository.findByFollowerArtWork(accountId, category, lastArtWorkId, pageable);
        if(accountId != null)
            setIsLike(accountId,artWorkList);
        return artWorkList;
    }

    private void isFollow(Long accountId, List<AccountResponseDto.TopArtist> topArtist) {
        topArtist.forEach((artist) -> {
            boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, artist.getAccount_id());
            if (isFollow)
                artist.setIsFollow();
        });
    }

    private List<AccountResponseDto.TopArtist> getTopArtist() {
        Pageable pageable = PageRequest.of(0,10);
        return accountRepository.findTopArtist(pageable);
    }

    private List<ArtworkMain> getArtworkList(String interest) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findArtWorkByMostViewAndMostLike(interest,pageable);
    }

    private void setImgUrl(List<CommonDto.ImgUrlDto> imgDto, ArtWorks artWork) {
        imgDto.forEach((img) -> {
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

    private void setIsLike(Long accountId, List<ArtworkMain> artWorkList) {
        artWorkList.forEach((artWork) -> {
            artWork.setLikeCountAndIsLike(artWorkLikesRepository.
                    existByAccountIdAndArtWorkId(accountId, artWork.getArtwork_id()));
        });
    }
}
