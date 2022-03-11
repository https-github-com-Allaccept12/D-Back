package TeamDPlus.code.controller;

import TeamDPlus.code.dto.Success;
import TeamDPlus.code.service.account.GoogleAccountService;
import TeamDPlus.code.service.account.KakaoAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final KakaoAccountService kakaoAccountService;
    private final GoogleAccountService googleAccountService;

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Success> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success<>(
                "로그인 성공", kakaoAccountService.kakaoLogin(code)), HttpStatus.OK);
    }

    @GetMapping("/user/google/callback")
    public ResponseEntity<Success> googleLogin(@RequestParam String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success<>(
                "로그인 성공", googleAccountService.googleLogin(code)), HttpStatus.OK);
    }

}
