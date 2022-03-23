package TeamDPlus.code.controller.account;

import TeamDPlus.code.dto.Success;
import TeamDPlus.code.service.account.GoogleAccountService;
import TeamDPlus.code.service.account.KakaoAccountService;
import TeamDPlus.code.service.account.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final KakaoAccountService kakaoAccountService;
    private final GoogleAccountService googleAccountService;
    private final SecurityService securityService;

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Success> kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success<>(
                "로그인 성공", kakaoAccountService.kakaoLogin(code)), HttpStatus.OK);
    }

    @GetMapping("/user/google/callback")
    public ResponseEntity<Success> googleLogin(@RequestParam("code") String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success<>(
                "로그인 성공", googleAccountService.googleLogin(code)), HttpStatus.OK);
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<Success> refresh(@RequestHeader(value = "Authorization") String refreshToken, HttpServletRequest request) {
        return new ResponseEntity<>(new Success<>(
                "토큰 재발급 성공", securityService.refresh(refreshToken, request)), HttpStatus.OK);
    }

}
