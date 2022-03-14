package TeamDPlus.code.service.account;


import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.HistoryRequestDto;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountMyPageService {

    AccountResponseDto.AccountInfo showAccountInfo(Long visitAccountId, Long accountId, final Long lastArtWorkId);

    List<HistoryResponseDto.History> showAccountHistory(Long accountId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long lastArtWorkId, Long visitAccountId, Long accountId, boolean isPortfolio);

    void updateAccountInfo(final AccountRequestDto.UpdateAccountInfo dto, final Long accountId);

    void updateAccountHistory(List<HistoryRequestDto.HistoryUpdate> dto, final Long accountId);

    void updateAccountCareerFeed(ArtWorkRequestDto.ArtWorkPortFolioUpdate dto, final Long accountId);

    void updateAccountIntro(final AccountRequestDto.UpdateAccountIntro dto, final Long accountId);

    void updateAccountSpecialty(final AccountRequestDto.UpdateSpecialty dto, final Long accountId);

    void deleteAccountCareerFeed(Long artWorkId);

    void updateArtWorkScope(Long artWorkId, Long accountId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(Long lastArtWorkId,Long visitAccountId, Long accountId);

    Page<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(Long lastArtWorkId, Long accountId);




}
