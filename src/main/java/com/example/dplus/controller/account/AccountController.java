package com.example.dplus.controller.account;

import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.AccountRequestDto;
import com.example.dplus.service.account.GoogleAccountService;
import com.example.dplus.service.account.KakaoAccountService;
import com.example.dplus.service.account.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Success> refresh(@RequestBody AccountRequestDto.ToKen token) {
        System.out.println(token.getAccess_token());
        System.out.println(token.getRefresh_token());
        return new ResponseEntity<>(new Success<>(
                "토큰 재발급 성공", securityService.refresh(token.getAccess_token(), token.getRefresh_token())), HttpStatus.OK);
    }

}
