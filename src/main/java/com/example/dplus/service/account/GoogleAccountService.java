package com.example.dplus.service.account;

import com.example.dplus.domain.account.rank.Rank;
import com.example.dplus.domain.account.rank.RankRepository;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.AccountRepository;
import com.example.dplus.dto.GoogleUserInfoDto;
import com.example.dplus.dto.response.LoginResponseDto;
import com.example.dplus.jwt.JwtTokenProvider;
import com.example.dplus.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class GoogleAccountService {

    private final AccountRepository accountRepository;
    private final RankRepository rankRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public LoginResponseDto googleLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 구글 API 호출
        GoogleUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);

        // 3. 필요시에 회원가입, JWT 토큰 발행
        return registerGoogleUserIfNeeded(googleUserInfo);

    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "519093450264-n3cm4m0h2d95gpipnigtt2erhotp7l58.apps.googleusercontent.com");
        body.add("client_secret", "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");
        body.add("redirect_uri", "http://localhost:8081/user/google/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v4/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private GoogleUserInfoDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.POST,
                googleUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("sub").asText();
        String name = jsonNode.get("name").asText();
        String profileImage = jsonNode.get("picture").asText();
        String email = jsonNode.get("email").asText();
        String username = id + email;

        System.out.println("구글 사용자 정보: " + id + ", " + username + ", " + name + ", " + profileImage + ", " + email);
        return new GoogleUserInfoDto(id, name, profileImage, email, username);
    }

    private LoginResponseDto registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {
        // DB 에 중복된 Google Id 가 있는지 확인
        String email = googleUserInfo.getEmail();
        String username = googleUserInfo.getEmail();

        Account googleUser = accountRepository.findByAccountName(username)
                .orElse(null);
        if (googleUser == null) {
            // 회원가입
            String name = googleUserInfo.getName();
            String profileImg = googleUserInfo.getProfile_img();

            Rank rank = Rank.builder().rankScore(0L).build();
            Rank saveRank = rankRepository.save(rank);
            googleUser = Account.builder()
                    .accountName(username)
                    .nickname(name)
                    .profileImg(profileImg)
                    .email(email)
                    .other("")
                    .specialty("")
                    .rank(saveRank)
                    .build();
            accountRepository.save(googleUser);
        }

        String accessToken = jwtTokenProvider.createToken(Long.toString(googleUser.getId()), googleUser.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(googleUser.getId()));
//        googleUser.refreshToken(refreshToken);
        redisService.setValues(refreshToken, googleUser.getId());
        return LoginResponseDto.builder()
                .account_id(googleUser.getId())
                .profile_img(googleUser.getProfileImg())
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();

    }

}
