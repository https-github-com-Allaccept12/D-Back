package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.response.TokenResponseDto;
import TeamDPlus.code.jwt.JwtTokenProvider;
import TeamDPlus.code.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public TokenResponseDto refresh(String refreshToken, HttpServletRequest request) {
        // 리프레시 토큰 기간 만료 에러
        if (jwtTokenProvider.validateToken(request, refreshToken)) {
            throw new ApiRequestException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

        Long userPk = Long.parseLong(jwtTokenProvider.getUserPk(refreshToken));
        String getRefreshToken = redisService.getValues(userPk);
        Account account = accountRepository.findById(userPk)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));

//        String getRefreshToken = account.getRefreshToken();
        
        if (!refreshToken.equals(getRefreshToken)) {
            throw new ApiRequestException(ErrorCode.NO_MATCH_ITEM_ERROR);
        }

        String updateToken = jwtTokenProvider.createToken(Long.toString(account.getId()), account.getEmail());
        String updateRefreshToken = jwtTokenProvider.createRefreshToken(Long.toString(account.getId()));
        redisService.delValues(userPk);
        redisService.setValues(updateRefreshToken, userPk);
//        account.refreshToken(updateRefreshToken);

        return TokenResponseDto.builder()
                .accessToken(updateToken)
                .refreshToken(updateRefreshToken)
                .build();
    }

}
