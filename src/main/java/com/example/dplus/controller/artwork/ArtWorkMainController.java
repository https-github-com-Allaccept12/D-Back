package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.artwork.ArtworkMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtWorkMainController {

    private final ArtworkMainService artworkMainService;

    @GetMapping("/")
    public ResponseEntity<Success> main(@AuthenticationPrincipal UserDetailsImpl user) {
        Long accountId ;
        String interest = "default";
        if (user != null) {
            accountId = user.getUser().getId();
            interest = user.getUser().getInterest();
        } else {
            accountId = 0L;
        }
        return new ResponseEntity<>(new Success("메인 페이지",
                artworkMainService.mostPopularArtWork(accountId,interest)), HttpStatus.OK);
    }

    @GetMapping("/api/artwork/{last_artwork_id}")
    public ResponseEntity<Success> artWorkMain(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable Long last_artwork_id) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("둘러보기",
                artworkMainService.showArtworkMain(accountId,last_artwork_id,"")),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/category/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkCategory(@AuthenticationPrincipal UserDetailsImpl user,
                                                   @PathVariable String category,
                                                   @PathVariable Long last_artwork_id) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("카테고리별 작업물",
                artworkMainService.showArtworkMain(accountId,last_artwork_id,category)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort/{category}")
    public ResponseEntity<Success> artWorkLikeSort(@AuthenticationPrincipal UserDetailsImpl user,
                                                   @PathVariable String category,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("좋아요 정렬한 작업물",
                artworkMainService.showArtWorkLikeSort(accountId,category,page,size)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort-follow/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSortFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long last_artwork_id,
                                                     @PathVariable String category) {
        if (user != null) {
            return new ResponseEntity<>(new Success("팔로우한 작가 작업물",
                    artworkMainService.findByFollowerArtWork(user.getUser().getId(), category, last_artwork_id)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PostMapping("/api/artwork")
    public ResponseEntity<Success> createArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @RequestPart ArtWorkCreate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        if (user != null) {
            return new ResponseEntity<>(new Success("작품 등록 완료"
                    ,artworkMainService.createArtwork(user.getUser().getId(),data, imgFile)),HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> updateArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id,
                                                 @Valid @RequestPart ArtWorkUpdate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        if (user != null) {
            return new ResponseEntity<>(new Success("작품 수정 완료",
                    artworkMainService.updateArtwork(user.getUser().getId(),artwork_id,data, imgFile)),HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> deleteArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id) {
        if (user != null) {
            artworkMainService.deleteArtwork(user.getUser().getId(), artwork_id);
            return new ResponseEntity<>(new Success("작품 삭제",""),HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @GetMapping("/api/artwork/detail/{artwork_id}")
    public ResponseEntity<Success> artWorkDetail(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("작품 상세",
                artworkMainService.detailArtWork(accountId,artwork_id)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/search/{last_artwork_id}/{keyword}")
    public ResponseEntity<Success> artWorkSearch(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long last_artwork_id,
                                                 @PathVariable String keyword) {
        Long accountId = getaLong(user);
        if (keyword == null) {
            throw new ErrorCustomException(ErrorCode.NON_KEYWORD_ERROR);
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                artworkMainService.findBySearchKeyWord(keyword,last_artwork_id,accountId)),HttpStatus.OK);
    }

    private Long getaLong(UserDetailsImpl user) {
        Long accountId ;
        if (user != null) {
            accountId = user.getUser().getId();
        } else {
            accountId = 0L;
        }
        return accountId;
    }


}
