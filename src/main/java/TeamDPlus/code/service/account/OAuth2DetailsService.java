package TeamDPlus.code.service.account;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import java.util.Optional;

import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.jwt.provider.GoogleUserInfo;
import TeamDPlus.code.jwt.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    // userRequest 는 code를 받아서 accessToken을 응답 받은 객체
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

        // code를 통해 구성한 정보
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청~~");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("우리는 구글과 페이스북만 지원해요 ㅎㅎ");
        }

        Optional<Account> accountOptional =
                accountRepository.findByEmail(oAuth2UserInfo.getEmail());

        Account account = Account.builder()
                .nickname(oAuth2UserInfo.getName())
                .profileImg(oAuth2UserInfo.getProfileImg())
                .email(oAuth2UserInfo.getEmail())
                .build();

        if (!accountOptional.isPresent()) {
            accountRepository.save(account);
        }

        return new UserDetailsImpl(account, oAuth2User.getAttributes());
    }
}
