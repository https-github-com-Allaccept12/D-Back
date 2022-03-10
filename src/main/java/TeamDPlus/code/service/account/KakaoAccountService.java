package TeamDPlus.code.service.account;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.KakaoUserInfoDto;
import TeamDPlus.code.dto.response.LoginResponseDto;
import TeamDPlus.code.jwt.JwtTokenProvider;
import TeamDPlus.code.jwt.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoAccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        Account kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. 강제 로그인 처리
        return forceLogin(kakaoUser);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "5c2af632e5eb943eadbf20d0c4006bdb");
        body.add("redirect_uri", "http://localhost:8081/user/kakao/callback");
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

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + profileImage + ", " + email);
        return new KakaoUserInfoDto(id, nickname, profileImage, email);
    }

    private Account registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String email = kakaoUserInfo.getEmail();

        Account kakaoUser = accountRepository.findByEmail(email)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            String nickname = kakaoUserInfo.getNickname();

            String profileImg = kakaoUserInfo.getProfile_img();

            kakaoUser = Account.builder().nickname(nickname).profileImg(profileImg).email(email).build();
            accountRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    private LoginResponseDto forceLogin(Account kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.createToken(Long.toString(kakaoUser.getId()), kakaoUser.getEmail());
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .account_id(kakaoUser.getId())
                .profile_img(kakaoUser.getProfileImg())
                .token(token)
                .build();
        return responseDto;
    }

}
