package TeamDPlus.code.service.account.mypage;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.request.HistoryRequestDto;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountMyPageServiceImpl implements AccountMyPageService{

    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final ArtWorkRepository artWorkRepository;
    private final FollowRepository followRepository;


    //마이페이지
    @Transactional(readOnly = true)
    public AccountResponseDto.AccountInfo showAccountInfo(final Long visitAccountId, final Long accountId, final Long lastArtWorkId) {
        final Account findAccount = accountRepository.findById(accountId).orElseThrow(() -> new IllegalStateException("존재 하지않는 사용자 입니다."));
        final Long follower = followRepository.countByFollowerId(findAccount.getId());
        final Long following = followRepository.countByFollowingId(findAccount.getId());
        final boolean isFollow= followRepository.existsByFollowerIdAndAndFollowingId(visitAccountId,accountId);
        return AccountResponseDto.AccountInfo.from(findAccount,follower,following, isFollow,visitAccountId.equals(accountId));
    }
    //연혁
    @Transactional(readOnly = true)
    public List<HistoryResponseDto.History> showAccountHistory(Long accountId) {
        final List<History> history = historyRepository.findAllByAccountId(accountId);
        return history.stream()
                .map(HistoryResponseDto.History::new)
                .collect(Collectors.toList());
    }
    //작업물 - 포트폴리오
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long lastArtWorkId, Long visitAccountId, Long accountId, boolean isPortfolio) {
        final Pageable pageable = PageRequest.of(0,5);
        return artWorkRepository.findByArtWorkImageAndAccountId(lastArtWorkId,pageable,visitAccountId,accountId,true);
    }

    //포트폴리오 - 기본 유저정보 수정
    @Transactional
    public void updateAccountInfo(final AccountRequestDto.UpdateAccountInfo dto,final Long accountId) {
        Account account = getAccount(accountId);
        //프로필 기본 설정 update
        account.updateInfo(dto);
    }
    //포트폴리오 - 기본 소개 수정
    @Override
    public void updateAccountIntro(AccountRequestDto.UpdateAccountIntro dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateIntro(dto);
    }

    //포트폴리오 - 스킬셋 수정
    @Override
    public void updateAccountSpecialty(AccountRequestDto.UpdateSpecialty dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateSpecialty(dto);
    }

    @Transactional
    public void updateAccountHistory(List<HistoryRequestDto.HistoryUpdate> dto, Long accountId) {
        //히스토리 전체 삭제 벌크
        Account account = getAccount(accountId);
        historyRepository.deleteAllByAccountId(accountId);
        //다시셋팅
        List<History> collect = dto.stream()
                .map(data -> History.toEntity(data,account))
                .collect(Collectors.toList());
        historyRepository.saveAll(collect);
    }

    //포트폴리오에 올라오는거
    @Transactional
    public void updateAccountCareerFeed(ArtWorkRequestDto.ArtWorkPortFolioUpdate dto, Long accountId) {
        //유저가 원하는 작품들 Is_Master를 True로 벌크
        artWorkRepository.updateAllArtWorkIsMasterToTrue(dto.getArtwork_feed());
    }

    @Transactional
    public void deleteAccountCareerFeed(Long artWorkId) {
        artWorkRepository.updateArtWorkIdMasterToFalse(artWorkId);
    }

    //scope조정
    public void updateArtWorkScope(Long artWorkId, Long accountId) {
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        artWorks.updateArtWorkIsScope();
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

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }






}





















