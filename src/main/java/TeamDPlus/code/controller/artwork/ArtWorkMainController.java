package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.common.CommonDto.ArtWorkKeyword;
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkCreateAndUpdate;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.artwork.ArtworkMainService;
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
    private final int SORT_SIGN_LIKE = 2;
    private final ArtworkMainService artworkMainService;

    @GetMapping("/")
    public ResponseEntity<Success> main(@AuthenticationPrincipal UserDetailsImpl user) {
        if (user == null) {
            return new ResponseEntity<>(new Success("메인 페이지",
                    artworkMainService.mostPopularArtWork(null)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("메인 페이지",
                artworkMainService.mostPopularArtWork(user.getUser().getId())), HttpStatus.OK);
    }

    @GetMapping("/api/artwork/{last_artwork_id}")
    public ResponseEntity<Success> artWorkMain(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable Long last_artwork_id) {
        if (user == null) {
            return new ResponseEntity<>(new Success("둘러보기",
                    artworkMainService.showArtworkMain(null,last_artwork_id,"",SORT_SIGN_LATEST)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("둘러보기",
                artworkMainService.showArtworkMain(user.getUser().getId(),last_artwork_id,"",SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/category/{category}/{last_artwork_id}/")
    public ResponseEntity<Success> artWorkCategory(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable String category,
                                               @PathVariable Long last_artwork_id) {
        if (user == null) {
            return new ResponseEntity<>(new Success("카테고리별 작업물",
                    artworkMainService.showArtworkMain(null,last_artwork_id,category,SORT_SIGN_LATEST)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("카테고리별 작업물",
                artworkMainService.showArtworkMain(user.getUser().getId(),last_artwork_id,category,SORT_SIGN_LATEST)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort/{category}/{sortsign}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSort(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable int sortsign,
                                               @PathVariable Long last_artwork_id,
                                               @PathVariable String category) {
        if (user == null) {
            return new ResponseEntity<>(new Success("카테고리별 정렬한 작업물",
                    artworkMainService.showArtworkMain(null,last_artwork_id,category,sortsign)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("카테고리별 정렬한 작업물",
                artworkMainService.showArtworkMain(user.getUser().getId(),last_artwork_id,category,sortsign)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/sort-follow/{category}/{last_artwork_id}")
    public ResponseEntity<Success> artWorkSortFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                               @PathVariable Long last_artwork_id,
                                               @PathVariable String category) {
        if (user != null){
            return new ResponseEntity<>(new Success("팔로우한 작가 작업물",
                    artworkMainService.findByFollowerArtWork(user.getUser().getId(),category,last_artwork_id)),HttpStatus.OK);
        }
        throw new IllegalStateException("로그인을 안했거나, 로그인이 만료되었습니다.");

    }

    //대표작품설정
    public static class testDto {
        private List<MultipartFile> imgFile;
        private ArtWorkCreateAndUpdate data;
    }


    @PostMapping("/api/artwork")
    public ResponseEntity<Success> createArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @RequestPart ArtWorkCreateAndUpdate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        return new ResponseEntity<>(new Success("작품 등록 완료"
                ,artworkMainService.createArtwork(user.getUser().getId(),data, imgFile)),HttpStatus.OK);
    }

    @PatchMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> updateArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id,
                                                 @RequestPart ArtWorkCreateAndUpdate data,
                                                 @RequestPart List<MultipartFile> imgFile) {
        return new ResponseEntity<>(new Success("작품 수정 완료",
                artworkMainService.updateArtwork(user.getUser().getId(),artwork_id,data, imgFile)),HttpStatus.OK);
    }

    @DeleteMapping("/api/artwork/{artwork_id}")
    public ResponseEntity<Success> deleteArtWork(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id) {
        artworkMainService.deleteArtwork(user.getUser().getId(), artwork_id);
        return new ResponseEntity<>(new Success("작품 삭제",""),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/detail/{artwork_id}")
    public ResponseEntity<Success> artWorkDetail(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long artwork_id) {
        if (user == null) {
            return new ResponseEntity<>(new Success("작품 상세",
                    artworkMainService.detailArtWork(null,artwork_id)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("작품 상세",
                artworkMainService.detailArtWork(user.getUser().getId(),artwork_id)),HttpStatus.OK);
    }

    @GetMapping("/api/artwork/search/{last_artwork_id}/{keyword}")
    public ResponseEntity<Success> artWorkSearch(@AuthenticationPrincipal UserDetailsImpl user,
                                                 @PathVariable Long last_artwork_id,
                                                 @PathVariable String keyword) {
        if (keyword == null) {
            throw new IllegalStateException("검색어를 입력 해주세요.");
        }
        if (user == null) {
            return new ResponseEntity<>(new Success("작품 검색 완료",
                    artworkMainService.findBySearchKeyWord(keyword,last_artwork_id,null)),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                artworkMainService.findBySearchKeyWord(keyword,last_artwork_id,user.getUser().getId())),HttpStatus.OK);
    }

}
