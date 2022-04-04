package com.example.dplus.service.account;


import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.AccountRequestDto;
import com.example.dplus.dto.request.ArtWorkRequestDto;
import com.example.dplus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.example.dplus.dto.response.HistoryResponseDto;

import java.util.List;


public interface AccountMyPageService {

    AccountResponseDto.AccountInfo showAccountInfo(Long visitAccountId, Long accountId);

    List<HistoryResponseDto.History> showAccountHistory(Long accountId);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long lastArtWorkId, Long visitAccountId, Long accountId);

    void updateAccountHistory(final HistoryUpdateList dto, final Long accountId);

    void updateAccountCareerFeedList(ArtWorkRequestDto.ArtWorkPortFolioUpdate dto);

    void updateAccountIntro(final AccountRequestDto.UpdateAccountIntro dto, final Long accountId);

    void updateAccountSpecialty(final AccountRequestDto.UpdateSpecialty dto, final Long accountId);

    void masterAccountCareerFeed(Long artWorkId, Account account);
    void nonMasterAccountCareerFeed(Long artWorkId,Account account);
    void hideArtWorkScope(Long artWorkId, Account account);
    void nonHideArtWorkScope(Long artWorkId, Account account);

    List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long lastArtWorkId,final Long visitAccountId,final Long accountId);

    List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(final Long lastArtWorkId,final Long accountId);

    List<AccountResponseDto.MyPost> getMyPost(Long accountId, String board);

    List<AccountResponseDto.MyPost> getMyBookMarkPost(Long accountId, String board);

    List<AccountResponseDto.MyAnswer> getMyAnswer(Long accountId);

    List<AccountResponseDto.MyComment> getMyComment(Long accountId);

}
