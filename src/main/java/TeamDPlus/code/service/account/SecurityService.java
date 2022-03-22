package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.response.TokenResponseDto;
import TeamDPlus.code.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponseDto refresh(String refreshToken) {
        // 리프레시 토큰 기간 만료 에러
        if (jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalStateException("리프레시 토큰 기간 만료");
        }

        Long userPk = Long.parseLong(jwtTokenProvider.getUserPk(refreshToken));
        Account account = accountRepository.findById(userPk)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));

        String getRefreshToken = account.getRefreshToken();
        
        if (!refreshToken.equals(getRefreshToken)) {
            throw new IllegalStateException("리프레시 토큰이 일치하지 않습니다.");
        }

        String updateToken = jwtTokenProvider.createToken(Long.toString(account.getId()), account.getEmail());
        String updateRefreshToken = jwtTokenProvider.createRefreshToken(Long.toString(account.getId()));
        account.refreshToken(updateRefreshToken);

        return TokenResponseDto.builder()
                .accessToken(updateToken)
                .refreshToken(updateRefreshToken)
                .build();
    }

}
