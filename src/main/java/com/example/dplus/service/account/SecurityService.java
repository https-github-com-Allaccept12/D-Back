package com.example.dplus.service.account;

import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.dto.response.TokenResponseDto;
import com.example.dplus.jwt.JwtTokenProvider;
import com.example.dplus.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public TokenResponseDto refresh(String accessToken, String refreshToken) {
        // 리프레시 토큰 기간 만료 에러
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new ApiRequestException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

        Long userPk = Long.parseLong(jwtTokenProvider.getUserPk(refreshToken));
        String getRefreshToken = redisService.getValues(userPk);
        Account account = accountRepository.findById(userPk)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));

        if (jwtTokenProvider.validateRefreshToken(accessToken)) {
            redisService.delValues(userPk);
            throw new ApiRequestException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

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
