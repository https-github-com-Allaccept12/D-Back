package com.example.TeamDPlus.controller.artwork;


import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.dto.Success;
import com.example.TeamDPlus.jwt.UserDetailsImpl;
import com.example.TeamDPlus.service.artwork.ArtworkMainService;
import com.example.TeamDPlus.dto.request.AccountRequestDto;
import com.example.TeamDPlus.dto.request.ArtWorkRequestDto;
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

    @GetMapping("/")
    public ResponseEntity<Success> main(@RequestBody AccountRequestDto.AccountVisit dto) {
        return new ResponseEntity<>(new Success("메인 페이지",
                artworkMainService.mostPopularArtWork(dto.getAccount_id())), HttpStatus.OK);
    }

    @GetMapping("/api/artwork/{last_artwork_id}")
    public ResponseEntity<Success> artWorkMain(@RequestBody AccountRequestDto.AccountVisit dto,
                                               @PathVariable Long last_artwork_id) {

        return new ResponseEntity<>(new Success("둘러보기",
                artworkMainService.showArtworkMain(dto.getAccount_id(),last_artwork_id,"",SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/category/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkCategory(@RequestBody AccountRequestDto.AccountVisit dto,
                                                   @PathVariable String category,
                                                   @PathVariable Long last_artwork_id) {

        return new ResponseEntity<>(new Success("카테고리별 작업물",
                artworkMainService.showArtworkMain(dto.getAccount_id(),last_artwork_id,category,SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort/{category}/{sortsign}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSort(@RequestBody AccountRequestDto.AccountVisit dto,
                                               @PathVariable int sortsign,
                                               @PathVariable Long last_artwork_id,
                                               @PathVariable String category) {

        return new ResponseEntity<>(new Success("카테고리별 정렬한 작업물",
                artworkMainService.showArtworkMain(dto.getAccount_id(),last_artwork_id,category,sortsign)),HttpStatus.OK);
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
                                                 @RequestPart ArtWorkRequestDto.ArtWorkCreate data,
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
                                                 @RequestPart ArtWorkRequestDto.ArtWorkUpdate data,
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
    public ResponseEntity<Success> artWorkDetail(@RequestBody AccountRequestDto.AccountVisit dto,
                                                 @PathVariable Long artwork_id) {
        return new ResponseEntity<>(new Success("작품 상세",
                artworkMainService.detailArtWork(dto.getAccount_id(),artwork_id)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/search/{last_artwork_id}/{keyword}")
    public ResponseEntity<Success> artWorkSearch(@RequestBody AccountRequestDto.AccountVisit dto,
                                                 @PathVariable Long last_artwork_id,
                                                 @PathVariable String keyword) {
        if (keyword == null) {
            throw new IllegalStateException("검색어를 입력 해주세요.");
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                artworkMainService.findBySearchKeyWord(keyword,last_artwork_id,dto.getAccount_id())),HttpStatus.OK);
    }

}
