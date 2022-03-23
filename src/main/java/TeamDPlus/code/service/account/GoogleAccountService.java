package TeamDPlus.code.service.account;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.domain.account.orthers.Other;
import TeamDPlus.code.domain.account.orthers.OtherRepository;
import TeamDPlus.code.domain.account.rank.Rank;
import TeamDPlus.code.domain.account.rank.RankRepository;
import TeamDPlus.code.dto.GoogleUserInfoDto;
import TeamDPlus.code.dto.response.LoginResponseDto;
import TeamDPlus.code.jwt.JwtTokenProvider;
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
    private final OtherRepository otherRepository;

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
        Long id = jsonNode.get("sub").asLong();
        String name = jsonNode.get("name").asText();
        String profileImage = jsonNode.get("picture").asText();
        String email = jsonNode.get("email").asText();

        System.out.println("구글 사용자 정보: " + id + ", " + name + ", " + profileImage + ", " + email);
        return new GoogleUserInfoDto(id, name, profileImage, email);
    }

    private LoginResponseDto registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {
        // DB 에 중복된 Google Id 가 있는지 확인
        String email = googleUserInfo.getEmail();

        Account googleUser = accountRepository.findByEmail(email)
                .orElse(null);
        if (googleUser == null) {
            // 회원가입
            String name = googleUserInfo.getName();
            String profileImg = googleUserInfo.getProfile_img();

            Rank rank = Rank.builder().rankScore(0L).build();
            Rank saveRank = rankRepository.save(rank);
            Specialty specialty = new Specialty();
            Other other = Other.builder().specialty(specialty).build();
            Other saveOther = otherRepository.save(other);
            googleUser = Account.builder()
                    .nickname(name)
                    .profileImg(profileImg)
                    .email(email)
                    .specialty(specialty)
                    .rank(saveRank)
                    .other(saveOther)
                    .build();
            accountRepository.save(googleUser);
        }

        String accessToken = jwtTokenProvider.createToken(Long.toString(googleUser.getId()), googleUser.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(googleUser.getId()));
        googleUser.refreshToken(refreshToken);
        return LoginResponseDto.builder()
                .account_id(googleUser.getId())
                .profile_img(googleUser.getProfileImg())
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();

    }

}
