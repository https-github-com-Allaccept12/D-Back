package com.example.dplus.controller.account;


import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
import com.example.dplus.dto.request.AccountRequestDto.UpdateAccountIntro;
import com.example.dplus.dto.request.AccountRequestDto.UpdateSpecialty;
import com.example.dplus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.account.AccountMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class AccountMyPageController {

    private final AccountMyPageService accountMyPageService;

    @GetMapping("")
    public ResponseEntity<Success> accountInfo(@RequestParam(value = "visitor_account_id",required = false) Long user,
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
                                                      @RequestParam(value = "visitor_account_id",required = false) Long user,
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
                                                       @RequestParam(value = "visitor_account_id") Long user) {
        return new ResponseEntity<>(new Success("작품 북마크 목록",
                accountMyPageService.showAccountArtWorkBookMark(last_artwork_id,user)),HttpStatus.OK);
    }

    @PostMapping("/masterpiece")
    public ResponseEntity<Success> masterpieceSelect(@RequestBody Id artworkId,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.masterAccountCareerFeed(artworkId.getId(),user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 작품 선택/해지",""),HttpStatus.OK);
    }

//    @PatchMapping("/masterpiece")
//    public ResponseEntity<Success> updateMasterpiece(@RequestBody Id artworkId,
//                                                     @RequestBody AccountMasterPiece prev_artwork_id,
//                                                     @AuthenticationPrincipal UserDetailsImpl user) {
//        accountMyPageService.updateMasterAccountCareerFeed(artworkId.getId(),prev_artwork_id.getPrev_artwork_id(),user.getUser());
//        return new ResponseEntity<>(new Success("포트폴리오 작품 수정",""),HttpStatus.OK);
//    }

    @PostMapping("/hidepiece")
    public ResponseEntity<Success> hidePieceClear(@RequestBody Id artworkId,
                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.hideArtWorkScope(artworkId.getId(),user.getUser());
        return new ResponseEntity<>(new Success("작품 보이기/숨기기",""),HttpStatus.OK);
    }

    @GetMapping("/community/myPost/{board}")
    public ResponseEntity<Success> myPost(@PathVariable String board,
                                          @RequestParam("start") int start,
                                          @RequestParam("visitor_account_id") Long user) {
        return new ResponseEntity<>(new Success("나의 게시글 목록",
                accountMyPageService.getMyPost(user,board,start)),HttpStatus.OK);
    }

    @GetMapping("/community/post/bookmark/{board}")
    public ResponseEntity<Success> myMyBookMarkPost(@PathVariable String board,
                                                    @RequestParam("start") int start,
                                                    @RequestParam("visitor_account_id") Long user) {
        return new ResponseEntity<>(new Success("내가 스크랩한 글 목록",
                accountMyPageService.getMyBookMarkPost(user,board,start)),HttpStatus.OK);
    }

    @GetMapping("/community/myanswer")
    public ResponseEntity<Success> getMyAnswer(@RequestParam("visitor_account_id") Long user,
                                               @RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("내 답글 목록",
                accountMyPageService.getMyAnswer(user,start)),HttpStatus.OK);
    }

    @GetMapping("/community/mycomment")
    public ResponseEntity<Success> getMyComment(@RequestParam("visitor_account_id") Long user,
                                                @RequestParam("start") int start) {
        return new ResponseEntity<>(new Success("내 댓글 목록",
                accountMyPageService.getMyComment(user,start)),HttpStatus.OK);
    }
    private Long getaLong(Long user) {
        long accountId  = 0L;
        if (user != null) {
            accountId = user;
        }
        return accountId;
    }
}
