package TeamDPlus.code.service.account.follow;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.Follow;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.rank.Rank;
import TeamDPlus.code.domain.account.rank.RankRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.response.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;
    private final RankRepository rankRepository;

    @Transactional
    public void follow(Long following_id, Long accountId) {
        Account account = getAccount(accountId);
        if (followRepository.existsByFollowerIdAndFollowingId(account.getId(),following_id)) {
            throw new ApiRequestException("이미 팔로우한 사람 입니다.");
        }
        account.getRank().upRankScore();
        final Follow follow = Follow.builder().followerId(account.getId()).followingId(following_id).build();
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(Long unFollowing_id, Long accountId) {
        Account account = getAccount(accountId);
        if (!followRepository.existsByFollowerIdAndFollowingId(account.getId(), unFollowing_id)) {
          throw new ApiRequestException("이미 언팔로우한 사람 입니다.");
        }
        account.getRank().downRankScore();
        followRepository.deleteByFollowerIdAndFollowingId(account.getId(),unFollowing_id);
    }

    //accountId를 팔로잉 하고있는 사람들의 리스트
    @Transactional(readOnly = true)
    public List<FollowResponseDto.FollowList> findFollowingList(Long accountId) {
        return followRepository.findAllByFollowingId(accountId);
    }

    //accountId가 팔로잉 하고있는 사람들의 리스트
    @Transactional(readOnly = true)
    public List<FollowResponseDto.FollowList> findFollowerList(Long accountId) {
        return followRepository.findAllByFollowerId(accountId);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException("존재 하지않는 유저입니다."));
    }

}
