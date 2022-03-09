package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
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


    @Transactional(readOnly = true)
    public AccountResponseDto.AccountMyPageMain showAccountMyPage(Long accountId) {
        Account findAccount = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("존재 하지않는 사용자 입니다."));
        List<History> history = historyRepository.findAllByAccountId(findAccount.getId());
        List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed = artWorkRepository.findByArtWorkImageAndAccountId(findAccount.getId());
        Long follower = followRepository.countByFollowerId(findAccount.getId());
        Long following = followRepository.countByFollowingId(findAccount.getId());
        return AccountResponseDto.AccountMyPageMain.from(findAccount,history,artwork_feed,follower,following);
    }
}





















