package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.artwork.ArtworkMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtWorkMainController {

    private final ArtworkMainService artworkMainService;

    @GetMapping("/")
    public ResponseEntity<Success> main(@RequestParam(value = "visitor_account_id",required = false) Long user,
                                        @RequestParam(value = "interest",required = false) String interest) {
        long accountId = 0L;
        String interested = "default";
        if (user != null) {
            accountId = user;
            if (interest != null) {
                interested = interest;
            }
        }
        return new ResponseEntity<>(new Success("메인 페이지",
                artworkMainService.mostPopularArtWork(accountId,interested)), HttpStatus.OK);
    }

    @GetMapping("/api/artwork/{last_artwork_id}")
    public ResponseEntity<Success> artWorkMain(@PathVariable Long last_artwork_id) {
        return new ResponseEntity<>(new Success("모아보기",
                artworkMainService.showArtworkMain(last_artwork_id,"all")),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/category/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkCategory(@PathVariable String category,
                                                   @PathVariable Long last_artwork_id) {
        return new ResponseEntity<>(new Success("카테고리별 작업물",
                artworkMainService.showArtworkMain(last_artwork_id,category)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort/{category}")
    public ResponseEntity<Success> artWorkLikeSort(@PathVariable String category,
                                                   @RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("좋아요 정렬한 작업물",
                artworkMainService.showArtWorkLikeSort(category,start)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort-follow/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSortFollow(@RequestParam(value = "visitor_account_id") Long user,
                                                     @PathVariable Long last_artwork_id,
                                                     @PathVariable String category) {
        if (user != null) {
            return new ResponseEntity<>(new Success("팔로우한 작가 작업물",
                    artworkMainService.findByFollowerArtWork(user, category, last_artwork_id)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
//
    @PostMapping("/api/artwork")
    public ResponseEntity<Success> createArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @RequestPart List<MultipartFile> imgFile,
                                                 @RequestPart ArtWorkCreate data) {

        //이미지 파일 개수가 총 두개 이상일때 업로드 가능 -> 썸네일도 하나의 파일로 인식이 되기때문에 섬네일제외 파일하나를 더 업로드 해야함.
        if (imgFile.size() >= 2) {
            return new ResponseEntity<>(new Success("작품 등록 완료"
                    , artworkMainService.createArtwork(user.getUser().getId(), data, imgFile)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.PHOTO_UPLOAD_ERROR);
    }

    @PatchMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> updateArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id,
                                                 @RequestPart ArtWorkUpdate data,
                                                 @RequestPart(value = "imgFile",required = false) List<MultipartFile> imgFile) {
        if (user != null) {
            return new ResponseEntity<>(new Success("작품 수정 완료",
                    artworkMainService.updateArtwork(user.getUser().getId(),artwork_id,data, imgFile)),HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/artwork/del/{category}")
    public ResponseEntity<Success> deleteArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @RequestBody Id artworkId,
                                                 @PathVariable String category) {
        if (user != null) {
            artworkMainService.deleteArtwork(user.getUser().getId(), artworkId.getId(), category);
            return new ResponseEntity<>(new Success("작품 삭제",""),HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @GetMapping("/api/artwork/detail/{artwork_id}")
    public ResponseEntity<Success> artWorkDetail(@RequestParam(value = "visitor_account_id",required = false) Long user,
                                                 @PathVariable Long artwork_id) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("작품 상세",
                artworkMainService.detailArtWork(accountId,artwork_id)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/search/{last_artwork_id}/{keyword}")
    public ResponseEntity<Success> artWorkSearch(@PathVariable Long last_artwork_id,
                                                 @PathVariable String keyword) {
        if (keyword == null) {
            throw new ErrorCustomException(ErrorCode.NON_KEYWORD_ERROR);
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                artworkMainService.findBySearchKeyWord(keyword,last_artwork_id)),HttpStatus.OK);
    }

    private Long getaLong(Long user) {
        long accountId = 0L;
        if (user != null) {
            accountId = user;
        }
        return accountId;
    }


}
