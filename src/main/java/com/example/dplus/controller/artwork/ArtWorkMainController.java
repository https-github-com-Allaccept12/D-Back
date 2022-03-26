package com.example.dplus.controller.artwork;


import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtWorkMainController {

    private final int SORT_SIGN_LATEST = 1;
    private final ArtworkMainService artworkMainService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<Success> main(@RequestParam("account_id") Long account_id) {
        return new ResponseEntity<>(new Success("메인 페이지",
                artworkMainService.mostPopularArtWork(account_id)), HttpStatus.OK);
    }

    @GetMapping("/api/artwork/{last_artwork_id}")
    public ResponseEntity<Success> artWorkMain(@RequestParam("account_id") Long accountId,
                                               @PathVariable Long last_artwork_id) {

        return new ResponseEntity<>(new Success("둘러보기",
                artworkMainService.showArtworkMain(accountId,last_artwork_id,"",SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/category/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkCategory(@RequestParam("account_id") Long accountId,
                                                   @PathVariable String category,
                                                   @PathVariable Long last_artwork_id) {

        return new ResponseEntity<>(new Success("카테고리별 작업물",
                artworkMainService.showArtworkMain(accountId,last_artwork_id,category,SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort/{category}/{sortsign}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSort(@RequestParam("account_id") Long accountId,
                                               @PathVariable int sortsign,
                                               @PathVariable Long last_artwork_id,
                                               @PathVariable String category) {

        return new ResponseEntity<>(new Success("카테고리별 정렬한 작업물",
                artworkMainService.showArtworkMain(accountId,last_artwork_id,category,sortsign)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort-follow/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSortFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable Long last_artwork_id,
                                               @PathVariable String category) {
        if (user != null){
            return new ResponseEntity<>(new Success("팔로우한 작가 작업물",
                    artworkMainService.findByFollowerArtWork(user.getUser().getId(),category,last_artwork_id)),HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }

    @PostMapping("/api/artwork")
    public ResponseEntity<Success> createArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @RequestPart ArtWorkCreate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        if (user != null) {
            return new ResponseEntity<>(new Success("작품 등록 완료"
                    ,artworkMainService.createArtwork(user.getUser().getId(),data, imgFile)),HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> updateArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id,
                                                 @RequestPart ArtWorkUpdate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        if (user != null) {
            return new ResponseEntity<>(new Success("작품 수정 완료",
                    artworkMainService.updateArtwork(user.getUser().getId(),artwork_id,data, imgFile)),HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> deleteArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id) {
        if (user != null) {
            artworkMainService.deleteArtwork(user.getUser().getId(), artwork_id);
            return new ResponseEntity<>(new Success("작품 삭제",""),HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @GetMapping("/api/artwork/detail/{artwork_id}")
    public ResponseEntity<Success> artWorkDetail(@RequestParam("account_id") Long accountId,
                                                 @PathVariable Long artwork_id) {
        return new ResponseEntity<>(new Success("작품 상세",
                artworkMainService.detailArtWork(accountId,artwork_id)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/search/{last_artwork_id}/{keyword}")
    public ResponseEntity<Success> artWorkSearch(@RequestParam("account_id") Long accountId,
                                                 @PathVariable Long last_artwork_id,
                                                 @PathVariable String keyword) {
        if (keyword == null) {
            throw new IllegalStateException("검색어를 입력 해주세요.");
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                artworkMainService.findBySearchKeyWord(keyword,last_artwork_id,accountId)),HttpStatus.OK);
    }

}
