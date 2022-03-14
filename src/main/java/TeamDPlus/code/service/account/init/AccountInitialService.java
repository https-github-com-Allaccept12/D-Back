package TeamDPlus.code.service.account.init;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountInitialService {


    private final AccountRepository accountRepository;

    @Transactional
    public void setInitProfile(AccountRequestDto.InitProfileSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.setInitProfile(dto);
    }

    @Transactional
    public void setInitTendecy(AccountRequestDto.InitTendencySetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.initTendency(dto.getTendency());
    }

    @Transactional
    public void setInitInterest(AccountRequestDto.InitInterestSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateInterest(dto.getInterest());

    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
    }

}
