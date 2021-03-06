package com.example.dplus.service.artwork;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.artwork.ArtWorkImage;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.dplus.dto.response.AccountResponseDto.TopArtist;
import com.example.dplus.dto.response.ArtWorkResponseDto.*;
import com.example.dplus.dto.response.MainResponseDto;
import com.example.dplus.repository.BatchInsertRepository;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.follow.FollowRepository;
import com.example.dplus.repository.artwork.ArtWorkRepository;
import com.example.dplus.repository.artwork.bookmark.ArtWorkBookMarkRepository;
import com.example.dplus.repository.artwork.comment.ArtWorkCommentRepository;
import com.example.dplus.repository.artwork.image.ArtWorkImageRepository;
import com.example.dplus.repository.artwork.like.ArtWorkLikesRepository;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final BatchInsertRepository batchInsertRepository;

    @Transactional(readOnly = true)
    public MainResponseDto mostPopularArtWork(Long accountId, String interest) {
        List<ArtworkMain> artWorkList = getArtworkList(interest);
        List<TopArtist> topArtist = getTopArtist();
        if (accountId != 0) {
            isFollow(accountId,topArtist);
            return MainResponseDto.of(topArtist,artWorkList);
        }
        return MainResponseDto.of(topArtist,artWorkList);
    }

    //????????????
    @Transactional(readOnly = true)
    public List<ArtworkMain> showArtworkMain(Long lastArtWorkId,String category){
        return artWorkRepository.findAllArtWork(lastArtWorkId,category);
    }

    @Transactional(readOnly = true)
    public List<ArtworkMain> showArtWorkLikeSort(String category, int start) {
        Pageable pageable = PageRequest.of(start,10);
        return artWorkRepository.showArtWorkLikeSort(category,pageable);
    }

    @Transactional
    public ArtWorkDetail detailArtWork(Long accountId, Long artWorkId) {
        //?????? ????????? ????????????
        ArtWorks artWorks = artWorkRepository.findById(artWorkId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        //?????????
        artWorks.addViewCount();
        //?????? ?????????????????? ?????? ???????????? ????????????
        ArtWorkSubDetail artWorksSub = artWorkRepository.findByArtWorkSubDetail(artWorkId);
        //?????? ???????????? ????????????
        List<ArtWorkImage> imgList = artWorkImageRepository.findByArtWorksId(artWorkId);
        //?????? ????????? ????????????
        List<ArtWorkComment> commentList = artWorkCommentRepository.findArtWorkCommentByArtWorksId(artWorkId);
        //?????? ????????? ?????? ????????? ????????????
        List<ArtWorkSimilarWork> similarList = artWorkRepository
                .findSimilarArtWork(artWorks.getAccount().getId(),artWorks.getId());
        boolean isLike = false;
        boolean isBookmark = false;
        boolean isFollow = false;
        if (accountId != 0) {
            //?????? ?????????????????? ????????????????????? ???????????? ?????????
            isLike = artWorkLikesRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
            //?????? ?????????????????? ????????????????????? ???????????? ?????????
            isBookmark = artWorkBookMarkRepository.existByAccountIdAndArtWorkId(accountId, artWorkId);
            //?????? ?????????????????? ????????????????????? ???????????? ?????????
            isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, artWorks.getAccount().getId());
        }
        //?????????????????? ????????? ??????
        artWorksSub.setComment_count((long) commentList.size());
        return ArtWorkDetail.from(imgList,commentList,similarList,artWorksSub,isLike,isBookmark,isFollow);
    }

    @Transactional
    public int createArtwork(Long accountId, ArtWorkCreate dto, List<MultipartFile> multipartFiles) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        if (account.getArtWorkCreateCount() >= 1000) {
            throw new ErrorCustomException(ErrorCode.DAILY_WRITE_UP_BURN_ERROR);
        }
        ArtWorks saveArtwork = artWorkRepository.save(ArtWorks.of(account, dto));
        s3ImageUpload(multipartFiles,dto,saveArtwork);
        account.upArtworkCountCreate();
        return 5 - account.getArtWorkCreateCount();
    }

    @Transactional
    public Long updateArtwork(Long accountId, Long artworkId, ArtWorkUpdate dto, List<MultipartFile> multipartFiles) {
        ArtWorks artWorks = artworkValidation(accountId, artworkId);
        updateImg(multipartFiles, artWorks, dto);
        artWorks.updateArtWork(dto);
        //artWorks.setThumbnail(dto.getThumbnail());
        return artWorks.getId();
    }

    @Transactional
    public void deleteArtwork(Long accountId, Long artworkId, String category) {
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

    //?????? ??????
    @Transactional(readOnly = true)
    public List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId) {
        return artWorkRepository.findBySearchKeyWord(keyword,lastArtWorkId);
    }

    @Transactional(readOnly = true)
    public List<ArtworkMain> findByFollowerArtWork(Long accountId, String category, Long lastArtWorkId) {
        return artWorkRepository.findByFollowerArtWork(accountId, category, lastArtWorkId);
    }


    private void s3ImageUpload(List<MultipartFile> multipartFiles,ArtWorkCreate dto, ArtWorks saveArtwork) {
        List<ArtWorkImage> imgList = new ArrayList<>();
        boolean thumbnailCheck = true;
        for (MultipartFile file : multipartFiles) {
            boolean thumbnail = Objects.equals(file.getOriginalFilename(), dto.getThumbnail());
            String imgUrl = fileProcessService.uploadImage(file);
            if (thumbnail && thumbnailCheck) {
                thumbnailCheck = false;
                saveArtwork.updateArtWorkThumbnail(imgUrl);
                continue;
            }
            ArtWorkImage img = ArtWorkImage.builder().artWorks(saveArtwork).artworkImg(imgUrl).build();
            imgList.add(img);
        }
        batchInsertRepository.artWorkImageSaveAll(imgList);
    }

    private void updateImg( List<MultipartFile> multipartFiles, ArtWorks findArtWork, ArtWorkUpdate dto) {
        if(dto.getDelete_img().size() != 0){
            dto.getDelete_img().forEach((img) -> {
                artWorkImageRepository.deleteByArtworkImg(img.getImg_url());
                fileProcessService.deleteImage(img.getImg_url());
            });
        }
        if (multipartFiles != null) {
            List<ArtWorkImage> imgList = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                log.info(file.getOriginalFilename());
                boolean thumbnail = Objects.equals(file.getOriginalFilename(), dto.getThumbnail());
                String imgUrl = fileProcessService.uploadImage(file);
                if (thumbnail) {
                    fileProcessService.deleteImage(findArtWork.getThumbnail());
                    findArtWork.updateArtWorkThumbnail(imgUrl);
                    continue;
                }
                ArtWorkImage img = ArtWorkImage.builder().artWorks(findArtWork).artworkImg(imgUrl).build();
                imgList.add(img);
            }
            batchInsertRepository.artWorkImageSaveAll(imgList);
        }
    }
    private List<TopArtist> getTopArtist() {
        List<Account> topArtist = accountRepository.findTopArtist();
        return topArtist.stream()
                .map(TopArtist::new)
                .collect(Collectors.toList());
    }

    private List<ArtworkMain> getArtworkList(String interest) {
        return artWorkRepository.findArtWorkByMostViewAndMostLike(interest);
    }

    private ArtWorks artworkValidation(Long accountId, Long artworkId){
        ArtWorks artWorks = artWorkRepository.findById(artworkId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if(!artWorks.getAccount().getId().equals(accountId)){
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return artWorks;
    }
    private void isFollow(Long accountId, List<TopArtist> topArtist) {
        topArtist.forEach((artist) -> {
            boolean isFollow = followRepository.existsByFollowerIdAndFollowingId(accountId, artist.getAccount_id());
            if (isFollow)
                artist.setIsFollow();
        });
    }
}