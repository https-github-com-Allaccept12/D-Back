package com.example.dplus.controller.account;


import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.AccountRequestDto.AccountMasterPiece;
import com.example.dplus.dto.request.AccountRequestDto.UpdateAccountIntro;
import com.example.dplus.dto.request.AccountRequestDto.UpdateSpecialty;
import com.example.dplus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.account.AccountMyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
@Slf4j
public class AccountMyPageController {

    private final AccountMyPageService accountMyPageService;


    @GetMapping("")
    public ResponseEntity<Success> accountInfo(@AuthenticationPrincipal UserDetailsImpl user,
                                               @RequestParam("owner_account_id") Long ownerAccountId) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("마이페이지 기본정보 조회",
                accountMyPageService.showAccountInfo(ownerAccountId, accountId)), HttpStatus.OK);
    }

    @GetMapping("/career-feed")
    public ResponseEntity<Success> accountCareerFeed(@RequestParam("owner_account_id") Long ownerAccountId) {
        return new ResponseEntity<>(new Success("마이페이지 커리어피드 조회",
                accountMyPageService.showAccountCareerFeed(ownerAccountId)), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<Success> accountHistory(@RequestParam("owner_account_id") Long accountId) {

        return new ResponseEntity<>(new Success("마이페이지 연혁 조회",
                accountMyPageService.showAccountHistory(accountId)), HttpStatus.OK);
    }

    @RequestMapping(value = "/history", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> accountHistoryUpdate(@Valid @RequestBody HistoryUpdateList data,
                                                        @AuthenticationPrincipal UserDetailsImpl user) {

        accountMyPageService.updateAccountHistory(data,user.getUser().getId());
        return new ResponseEntity<>(new Success("연혁 수정",""),HttpStatus.OK);
    }

    @GetMapping("/artwork/{last_artwork_id}")
    public ResponseEntity<Success> accountArtWorkList(@PathVariable Long last_artwork_id,
                                                      @AuthenticationPrincipal UserDetailsImpl user,
                                                      @RequestParam("owner_account_id") Long ownerAccountId) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("유저 작품 목록",
                accountMyPageService.showAccountArtWork(last_artwork_id,ownerAccountId,accountId)),HttpStatus.OK);
    }

    @RequestMapping(value = "/intro", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> accountIntro(@Valid @RequestBody UpdateAccountIntro data,
                                                @AuthenticationPrincipal UserDetailsImpl user) {

        accountMyPageService.updateAccountIntro(data, user.getUser().getId());
        return new ResponseEntity<>(new Success("유저 소개 수정", ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/specialty", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> accountSpecialty(@RequestBody UpdateSpecialty data,
                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.updateAccountSpecialty(data,user.getUser().getId());
        return new ResponseEntity<>(new Success("스킬셋 수정",""),HttpStatus.OK);
    }
    //내 북마크
    @GetMapping("/bookmark/{last_artwork_id}")
    public ResponseEntity<Success> ArtWorkBookmarkList(@PathVariable Long last_artwork_id,
                                                       @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("작품 북마크 목록",
                accountMyPageService.showAccountArtWorkBookMark(last_artwork_id,user.getUser().getId())),HttpStatus.OK);
    }

    @PostMapping(value = "/masterpiece/{artwork_id}")
    public ResponseEntity<Success> masterpieceSelect(@PathVariable Long artwork_id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        System.out.println("포트폴리오 작품 설정"+user.getUser().getId());
        accountMyPageService.masterAccountCareerFeed(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 작품 선택",""),HttpStatus.OK);
    }

    @DeleteMapping(value = "/masterpiece/{artwork_id}")
    public ResponseEntity<Success> masterpieceClear(@PathVariable Long artwork_id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.nonMasterAccountCareerFeed(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 작품 해지",""),HttpStatus.OK);
    }
    @PatchMapping(value = "/masterpiece/{artwork_id}")
    public ResponseEntity<Success> updateMasterpiece(@PathVariable Long artwork_id,
                                                     @RequestBody AccountMasterPiece prev_artwork_id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.updateMasterAccountCareerFeed(artwork_id,prev_artwork_id.getPrev_artwork_id(),user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 작품 수정",""),HttpStatus.OK);
    }
    //보이기
    @PostMapping(value = "/hidepiece/{artwork_id}")
    public ResponseEntity<Success> hidePieceSelect(@PathVariable Long artwork_id,
                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.nonHideArtWorkScope(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("작품 보이기",""),HttpStatus.OK);
    }
    //숨기기
    @DeleteMapping(value = "/hidepiece/{artwork_id}")
    public ResponseEntity<Success> hidePieceClear(@PathVariable Long artwork_id,
                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.hideArtWorkScope(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("작품 숨기기",""),HttpStatus.OK);
    }

    @GetMapping("/community/myPost/{board}")
    public ResponseEntity<Success> myPost(@PathVariable String board,
                                          @RequestParam("start") int start,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("나의 Post",
                accountMyPageService.getMyPost(user.getUser().getId(), board,start)),HttpStatus.OK);
    }

    @GetMapping("/community/post/bookmark/{board}")
    public ResponseEntity<Success> myMyBookMarkPost(@PathVariable String board,
                                                    @RequestParam("start") int start,
                                                    @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("내가 스크랩한 글",
                accountMyPageService.getMyBookMarkPost(user.getUser().getId(), board,start)),HttpStatus.OK);
    }

    @GetMapping("/community/myanswer")
    public ResponseEntity<Success> getMyAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                               @RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("나의 답글",
                accountMyPageService.getMyAnswer(user.getUser().getId(),start)),HttpStatus.OK);
    }

    @GetMapping("/community/mycomment")
    public ResponseEntity<Success> getMyComment(@AuthenticationPrincipal UserDetailsImpl user,
                                                @RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("나의 댓글",
                accountMyPageService.getMyComment(user.getUser().getId(),start)),HttpStatus.OK);
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
