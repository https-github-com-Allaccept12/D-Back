package TeamDPlus.code.service.account.init;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountInitialService {


    private final AccountRepository accountRepository;

    @Transactional
    public Long setInitProfile(AccountRequestDto.InitProfileSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.setInitProfile(dto);
        return account.getId();
    }

    @Transactional
    public Long setInitTendency(AccountRequestDto.InitTendencySetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.initTendency(dto.getTendency());
        return account.getId();
    }

    @Transactional
    public Long setInitInterest(AccountRequestDto.InitInterestSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateInterest(dto.getInterest());
        return account.getId();
    }
    @Transactional(readOnly = true)
    public void getNickNameValidation(String nickname) {
        Account account = accountRepository.findByNickname(nickname).orElse(null);
        if (account != null){
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
        if (!Pattern.matches("^[A-Za-z0-9]{3,}$", nickname)){
            throw new ApiRequestException("닉네임 조건에 맞지 않음"); //닉네임 조건에 맞지않음
        }
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
    }

}
