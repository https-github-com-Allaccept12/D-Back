package TeamDPlus.code.service.account;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;

import java.util.List;


public interface AccountMyPageService {

    AccountResponseDto.AccountMyPageMain showAccountPage(Long visitAccountId, Long accountId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(Long visitAccountId, Long accountId);

    List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(Long accountId);


}
