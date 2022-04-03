package com.example.dplus.service.account;

import com.example.dplus.domain.account.Rank;
import com.example.dplus.repository.account.rank.RankRepository;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.dto.KakaoUserInfoDto;
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
public class KakaoAccountService {

    private final AccountRepository accountRepository;
    private final RankRepository rankRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입, JWT 토큰 발행
        return registerKakaoUserIfNeeded(kakaoUserInfo);

    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "5c2af632e5eb943eadbf20d0c4006bdb");
//        body.add("redirect_uri", "https://dplusday.com/user/kakao/callback");
        body.add("redirect_uri", "http://localhost:3000/user/kakao/callback");
        //body.add("redirect_uri", "http://localhost:8081/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String profileImage = jsonNode.get("properties")
                .get("profile_image").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String username = id + email;

        //System.out.println("카카오 사용자 정보: " + id + ", " + username + ", " + nickname + ", " + profileImage + ", " + email);
        return new KakaoUserInfoDto(id, nickname, profileImage, email, username);
    }

    private LoginResponseDto registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String email = kakaoUserInfo.getEmail();
        String username = kakaoUserInfo.getUsername();

        Account kakaoUser = accountRepository.findByAccountName(username)
                .orElse(null);

        boolean isSignUp = false;

        if (kakaoUser == null) {
            // 회원가입
            isSignUp = true;
            String nickname = kakaoUserInfo.getNickname();

            String profileImg = kakaoUserInfo.getProfile_img();

            Rank rank = Rank.builder().rankScore(0L).build();
            Rank saveRank = rankRepository.save(rank);
            kakaoUser = Account.builder()
                    .accountName(username)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .email(email)
                    .rank(saveRank)
                    .other("")
                    .specialty("")
                    .build();
            accountRepository.save(kakaoUser);
        }

        String accessToken = jwtTokenProvider.createToken(Long.toString(kakaoUser.getId()), kakaoUser.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(kakaoUser.getId()));
        redisService.setValues(refreshToken, kakaoUser.getId());
        return LoginResponseDto.builder()
                .account_id(kakaoUser.getId())
                .profile_img(kakaoUser.getProfileImg())
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .isSignUp(isSignUp)
                .build();
    }

}
