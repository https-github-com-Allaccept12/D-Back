package TeamDPlus.code.service.account;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountMyPageService {

    AccountResponseDto.AccountMyPageMain showAccountPage(Long visitAccountId, Long accountId, final Long lastArtWorkId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(Long lastArtWorkId,Long visitAccountId, Long accountId);

    Page<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(Long lastArtWorkId, Long accountId);


}
