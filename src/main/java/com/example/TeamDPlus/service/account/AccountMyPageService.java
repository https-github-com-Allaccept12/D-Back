package com.example.TeamDPlus.service.account;


import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.dto.request.AccountRequestDto;
import com.example.TeamDPlus.dto.request.ArtWorkRequestDto;
import com.example.TeamDPlus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.TeamDPlus.dto.response.AccountResponseDto;
import com.example.TeamDPlus.dto.response.ArtWorkResponseDto;
import com.example.TeamDPlus.dto.response.HistoryResponseDto;

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

    void setAccountMasterPiece(final Long accountId, final AccountRequestDto.setAccountMasterPiece materPiece);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long lastArtWorkId,final Long visitAccountId,final Long accountId);

    List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(final Long lastArtWorkId,final Long accountId);

    List<AccountResponseDto.MyPost> getMyPost(Long accountId, String board);

    List<AccountResponseDto.MyPost> getMyBookMarkPost(Long accountId, String board);

    List<AccountResponseDto.MyAnswer> getMyAnswer(Long accountId);

    List<AccountResponseDto.MyComment> getMyComment(Long accountId);

}
