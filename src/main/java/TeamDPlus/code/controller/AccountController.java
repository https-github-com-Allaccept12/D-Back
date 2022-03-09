package TeamDPlus.code.controller;

import TeamDPlus.code.service.KakaoAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final KakaoAccountService kakaoAccountService;

    @GetMapping("/oauth2/authorization/kakao")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoAccountService.kakaoLogin(code);
        return "redirect:/";
    }

}
