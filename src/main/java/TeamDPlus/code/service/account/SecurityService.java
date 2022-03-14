package TeamDPlus.code.service.account;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.response.TokenResponseDto;
import TeamDPlus.code.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponseDto reissue(String accessToken, String refreshToken) {
        // 리프레시 토큰 기간 만료 에러
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalStateException("리프레시 토큰 기간 만료");
        }
        System.out.println("in");
        Optional<Account> account = accountRepository.findByRefreshToken(refreshToken);
        System.out.println("in");
        String getRefreshToken;
        if (account.isPresent()) {
            getRefreshToken = account.get().getRefreshToken();
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        
        if (!refreshToken.equals(getRefreshToken)) {
            throw new IllegalStateException("리프레시 토큰이 일치하지 않습니다.");
        }

        String updateToken = jwtTokenProvider.createToken(Long.toString(account.get().getId()), account.get().getEmail());
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        String updateRefreshToken = jwtTokenProvider.createRefreshToken(refreshTokenValue);
        account.get().refreshToken(updateRefreshToken);

        TokenResponseDto responseDto = TokenResponseDto.builder()
                .accessToken(updateToken)
                .refreshToken(updateRefreshToken)
                .build();

        return responseDto;
    }

}
