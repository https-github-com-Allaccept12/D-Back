package TeamDPlus.code.controller.account;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.AccountRequestDto.InitInterestSetting;
import TeamDPlus.code.dto.request.AccountRequestDto.InitProfileSetting;
import TeamDPlus.code.dto.request.AccountRequestDto.InitTendencySetting;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.account.init.AccountInitialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountInitController {

    private final AccountInitialService accountInitialService;

    //프로필 설정
    @PostMapping("/profile")
    public ResponseEntity<Success<Long>> initProfile(@RequestBody InitProfileSetting initProfile,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success<>("프로필 설정 완료",
                accountInitialService.setInitProfile(initProfile,user.getUser().getId())), HttpStatus.OK);
    }
    //닉네임 중복검사
    @GetMapping("/profile/nickname/{nickname}")
    public ResponseEntity<Success<String>> initNickNameValid(@PathVariable String nickname) {
        accountInitialService.getNickNameValidation(nickname);
        return new ResponseEntity<>(new Success<>("사용 가능 닉네임",""),HttpStatus.OK);
    }
    //성향 설정
    @PostMapping("/tendency")
    public ResponseEntity<Success<Long>> initTendency(@RequestBody InitTendencySetting initTendency,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success<>("성향 설정 완료",
                accountInitialService.setInitTendency(initTendency,user.getUser().getId())),HttpStatus.OK);
    }
    //관심사 설정
    @PostMapping("/profile/interest")
    public ResponseEntity<Success<Long>> initInterest(@RequestBody InitInterestSetting initInterest,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success<>("관심사 설정 완료",
                accountInitialService.setInitInterest(initInterest, user.getUser().getId())), HttpStatus.OK);
    }

}








