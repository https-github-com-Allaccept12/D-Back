package TeamDPlus.code.service.account;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.HistoryRequestDto;
import TeamDPlus.code.dto.request.HistoryRequestDto.HistoryUpdateList;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountMyPageService {

    AccountResponseDto.AccountInfo showAccountInfo(Long visitAccountId, Long accountId);

    List<HistoryResponseDto.History> showAccountHistory(Long accountId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long lastArtWorkId, Long visitAccountId, Long accountId);

    void updateAccountHistory(final HistoryUpdateList dto, final Long accountId);

    void updateAccountCareerFeedList(ArtWorkRequestDto.ArtWorkPortFolioUpdate dto);

    void updateAccountIntro(final AccountRequestDto.UpdateAccountIntro dto, final Long accountId);

    void updateAccountSpecialty(final AccountRequestDto.UpdateSpecialty dto, final Long accountId);

    void masterAccountCareerFeed(Long artWorkId,Account account);
    void nonMasterAccountCareerFeed(Long artWorkId,Account account);
    void hideArtWorkScope(Long artWorkId, Account account);
    void nonHideArtWorkScope(Long artWorkId, Account account);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long lastArtWorkId,final Long visitAccountId,final Long accountId);

    List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(final Long lastArtWorkId,final Long accountId);




}
