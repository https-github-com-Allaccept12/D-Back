package TeamDPlus.code.controller.account;


import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountInitController {

    private final AccountInitialService accountInitialService;

    //프로필 설정
    @PostMapping("/profile")
    public ResponseEntity<Success<Long>> initProfile(@RequestPart InitProfileSetting data,
                                                     @RequestPart MultipartFile imgFile,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        if (imgFile != null) {
            return new ResponseEntity<>(new Success<>("프로필 설정 완료",
                    accountInitialService.setInitProfile(imgFile,data,user.getUser().getId())), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.PHOTO_UPLOAD_ERROR);
    }
    //프로필 수정
    @PatchMapping("/profile")
    public ResponseEntity<Success<Long>> updateProfile(@RequestPart InitProfileSetting data,
                                                       @RequestPart MultipartFile imgFile,
                                                       @AuthenticationPrincipal UserDetailsImpl user) {

        return new ResponseEntity<>(new Success<>("프로필 설정 완료",
                accountInitialService.updateProfile(imgFile, data, user.getUser().getId())), HttpStatus.OK);
    }
    //닉네임 중복검사
    @GetMapping("/profile/nickname/{nickname}")
    public ResponseEntity<Success<String>> initNickNameValid(@PathVariable("nickname") String nickname) {
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
    @RequestMapping(value = {"/profile/interest"},method = {RequestMethod.POST,RequestMethod.PATCH})
    public ResponseEntity<Success<Long>> initInterest(@RequestBody InitInterestSetting initInterest,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return new ResponseEntity<>(new Success<>("관심사 설정 완료",
                accountInitialService.setInitInterest(initInterest, user.getUser().getId())), HttpStatus.OK);
    }

}








