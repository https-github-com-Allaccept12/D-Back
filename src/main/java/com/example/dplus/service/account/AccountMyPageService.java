package com.example.dplus.service.account;


import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.AccountRequestDto.UpdateAccountIntro;
import com.example.dplus.dto.request.AccountRequestDto.UpdateSpecialty;
import com.example.dplus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.dplus.dto.response.AccountResponseDto.AccountInfo;
import com.example.dplus.dto.response.AccountResponseDto.MyAnswer;
import com.example.dplus.dto.response.AccountResponseDto.MyComment;
import com.example.dplus.dto.response.AccountResponseDto.MyPost;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtWorkBookMark;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtWorkFeed;
import com.example.dplus.dto.response.ArtWorkResponseDto.MyArtWork;
import com.example.dplus.dto.response.HistoryResponseDto.History;

import java.util.List;


public interface AccountMyPageService {

    AccountInfo showAccountInfo(Long visitAccountId, Long accountId);

    List<History> showAccountHistory(Long accountId);

    List<ArtWorkFeed> showAccountCareerFeed(Long visitAccountId);

    void updateAccountHistory(final HistoryUpdateList dto, final Long accountId);

    void updateAccountIntro(final UpdateAccountIntro dto, final Long accountId);

    void updateAccountSpecialty(final UpdateSpecialty dto, final Long accountId);

    void masterAccountCareerFeed(Long artWorkId, Account account);

    //void nonMasterAccountCareerFeed(Long artWorkId, Account account);

    void updateMasterAccountCareerFeed(Long artWorkId, Long prevArtWorkId, Account account);

    void hideArtWorkScope(Long artWorkId, Account account);

    //void nonHideArtWorkScope(Long artWorkId, Account account);

    //void setAccountMasterPiece(final Long accountId, final AccountRequestDto.setAccountMasterPiece materPiece);

    List<MyArtWork> showAccountArtWork(final Long lastArtWorkId, final Long visitAccountId, final Long accountId);

    List<ArtWorkBookMark> showAccountArtWorkBookMark(final Long lastArtWorkId, final Long accountId);

    List<MyPost> getMyPost(Long accountId, String board, int start);

    List<MyPost> getMyBookMarkPost(Long accountId, String board, int start);

    List<MyAnswer> getMyAnswer(Long accountId, int start);

    List<MyComment> getMyComment(Long accountId, int start);

}
