package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountMyPageServiceImpl implements AccountMyPageService{

    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final ArtWorkRepository artWorkRepository;
    private final FollowRepository followRepository;


    //마이페이지/포트폴리오
    @Transactional(readOnly = true)
    public AccountResponseDto.AccountMyPageMain showAccountPage(final Long visitAccountId, final Long accountId) {
        final Account findAccount = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("존재 하지않는 사용자 입니다."));
        final List<History> history = historyRepository.findAllByAccountId(findAccount.getId());
        final List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed = artWorkRepository.findByArtWorkImageAndAccountId(visitAccountId,findAccount.getId(),true);
        final Long follower = followRepository.countByFollowerId(findAccount.getId());
        final Long following = followRepository.countByFollowingId(findAccount.getId());
        return AccountResponseDto.AccountMyPageMain.from(findAccount,history,artwork_feed,follower,following);
    }
    //마이페이지/유저작품
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long visitAccountId, final Long accountId) {
        return artWorkRepository.findByArtWorkImageAndAccountId(visitAccountId,accountId,false);
    }

    //마이페이지/스크랩
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(final Long accountId) {
        return artWorkRepository.findArtWorkByBookMart(accountId);
    }

    //마이페이지/




}





















