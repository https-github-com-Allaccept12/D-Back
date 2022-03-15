package TeamDPlus.code.controller.account;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.AccountRequestDto.AccountIsMyPage;
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkPortFolioUpdate;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.account.AccountMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my-page")
public class AccountMyPageController {

    private final AccountMyPageService accountMyPageService;


    @GetMapping("")
    public ResponseEntity<Success> accountInfo(@RequestBody AccountIsMyPage accountId,
                                               @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("마이페이지 기본정보 조회",
                accountMyPageService.showAccountInfo(accountId.getAccount_id(), user.getUser().getId())), HttpStatus.OK);
    }

    @GetMapping("/career-feed/{last_artwork_id}")
    public ResponseEntity<Success> accountCareerFeed(@RequestBody AccountIsMyPage accountId,
                                                     @PathVariable String last_artwork_id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("마이페이지 커리어피드 조회",
                accountMyPageService.showAccountCareerFeed(Long.parseLong(last_artwork_id), accountId.getAccount_id(), user.getUser().getId())), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<Success> accountHistory(@RequestBody AccountIsMyPage accountId,
                                                  @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("마이페이지 연혁 조회",
                accountMyPageService.showAccountHistory(accountId.getAccount_id())), HttpStatus.OK);
    }

    @GetMapping("/artwork/{last_artwork_id}")
    public ResponseEntity<Success> accountArtWorkList(@PathVariable Long last_artwork_id,
                                                      @RequestBody AccountIsMyPage accountId,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success("유저 작품 목록",
                accountMyPageService.showAccountArtWork(last_artwork_id,accountId.getAccount_id(),user.getUser().getId())),HttpStatus.OK);
    }
    //내 디모


    //다건
    @RequestMapping(value = {"/career-feed"}, method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> createAndUpdateCareerFeed(@RequestBody ArtWorkPortFolioUpdate data,
                                                             @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.updateAccountCareerFeedList(data, user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 등록/수정 성공", ""), HttpStatus.OK);
    }
    //단건
    @RequestMapping(value = "/masterpiece/{artwork_id}",method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> masterpieceSelect(@PathVariable Long artwork_id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.updateAccountCareerFeed(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("포트폴리오 작품 선택",""),HttpStatus.OK);
    }

    @RequestMapping(value = "/hidepiece/{artwork_id}", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity<Success> hidepieceSelect(@PathVariable Long artwork_id,
                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        accountMyPageService.updateArtWorkScope(artwork_id,user.getUser());
        return new ResponseEntity<>(new Success("작품 숨기기/보이기",""),HttpStatus.OK);
    }


}
