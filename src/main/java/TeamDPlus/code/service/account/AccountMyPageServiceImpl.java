package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountMyPageServiceImpl implements AccountMyPageService{

    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final ArtWorkRepository artWorkRepository;
    private final FollowRepository followRepository;


    //마이페이지/포트폴리오
    @Transactional(readOnly = true)
    public AccountResponseDto.AccountMyPageMain showAccountPage(final Long visitAccountId, final Long accountId, final Long lastArtWorkId) {
        final Account findAccount = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("존재 하지않는 사용자 입니다."));
        final Pageable pageable = PageRequest.of(0,5);
        final List<History> history = historyRepository.findAllByAccountId(findAccount.getId());
        final List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed =
                artWorkRepository.findByArtWorkImageAndAccountId(lastArtWorkId,pageable,visitAccountId,findAccount.getId(),true);
        final Long follower = followRepository.countByFollowerId(findAccount.getId());
        final Long following = followRepository.countByFollowingId(findAccount.getId());
        final boolean isFollow= followRepository.existsByFollowerIdAndAndFollowingId(visitAccountId,accountId);
        return AccountResponseDto.AccountMyPageMain.from(findAccount,history,artwork_feed,follower,following, isFollow,visitAccountId.equals(accountId));
    }
    @Transactional
    public Long updateAccountPortfolio(final AccountRequestDto.PortfolioUpdate dto, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        //프로필 기본 설정 update
        account.updateProfile(dto);
        //히스토리 전체 삭제 벌크
        historyRepository.deleteAllByAccountId(accountId);

        dto.getHistory().forEach((history) -> {
            historyRepository.save(History.builder()
                            .companyName(history.getCompany_name())
                            .companyDepartment(history.getCompany_department())
                            .companyPosition(history.getCompany_position())
                            .achievements(history.getAchievements())
                            .workStart(history.getAchievements())
                            .workEnd(history.getWork_end())
                            .account(account)
                            .build());
        });
        List<Long> artworkId = dto.getArtWork_feed().stream()
                .map(ArtWorkRequestDto.ArtWorkPortFolioUpdate::getArtwork_id)
                .collect(Collectors.toList());
        //전체 해당 유저의 작품들의 Is_Master를 False로 벌크
        artWorkRepository.updateAllArtWorkIsMasterToFalse(accountId);
        //유저가 원하는 작품들 Is_Master를 True로 벌크
        artWorkRepository.updateAllArtWorkIsMasterToTrue(artworkId);
        return accountId;
    }

    //마이페이지/유저작품
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long lastArtWorkId,final Long visitAccountId, final Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findByArtWorkImageAndAccountId(lastArtWorkId,pageable,visitAccountId,accountId,false);
    }

    //마이페이지/스크랩
    @Transactional(readOnly = true)
    public Page<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(Long lastArtWorkId,final Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findArtWorkBookMarkByAccountId(lastArtWorkId,pageable,accountId);
    }






}





















