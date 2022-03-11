package TeamDPlus.code.service.account;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.GoogleUserInfoDto;
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

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleAccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponseDto googleLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 구글 API 호출
        GoogleUserInfoDto googleUserInfo = getGoogleUserInfo(accessToken);

        // 3. 필요시에 회원가입
        Account googleUser = registerGoogleUserIfNeeded(googleUserInfo);

        // 4. 강제 로그인 처리
        return forceLogin(googleUser);
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
        Long id = jsonNode.get("sub").asLong();
        String name = jsonNode.get("name").asText();
        String profileImage = jsonNode.get("picture").asText();
        String email = jsonNode.get("email").asText();

        System.out.println("구글 사용자 정보: " + id + ", " + name + ", " + profileImage + ", " + email);
        return new GoogleUserInfoDto(id, name, profileImage, email);
    }

    private Account registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {
        // DB 에 중복된 Google Id 가 있는지 확인
        String email = googleUserInfo.getEmail();

        Account googleUser = accountRepository.findByEmail(email)
                .orElse(null);
        if (googleUser == null) {
            // 회원가입
            String name = googleUserInfo.getName();

            String profileImg = googleUserInfo.getProfile_img();

            googleUser = Account.builder().nickname(name).profileImg(profileImg).email(email).build();
            accountRepository.save(googleUser);
        }
        return googleUser;
    }

    private LoginResponseDto forceLogin(Account googleUser) {
        UserDetails userDetails = new UserDetailsImpl(googleUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.createToken(Long.toString(googleUser.getId()), googleUser.getEmail());
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        String refreshToken = jwtTokenProvider.createRefreshToken(refreshTokenValue);
        googleUser.refreshToken(refreshToken);
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .account_id(googleUser.getId())
                .profile_img(googleUser.getProfileImg())
                .token(token)
                .build();
        return responseDto;
    }

}
