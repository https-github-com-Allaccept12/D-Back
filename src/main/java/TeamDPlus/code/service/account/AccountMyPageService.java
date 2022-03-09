package TeamDPlus.code.service.account;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.response.AccountResponseDto;


public interface AccountMyPageService {

    AccountResponseDto.AccountMyPageMain showAccountMyPage(Long accountId);


}
