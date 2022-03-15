package TeamDPlus.code.service.account.follow;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.Follow;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.response.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void follow(Long following_id, Account account) {
        if (followRepository.existsByFollowerIdAndFollowingId(account.getId(),following_id)) {
            throw new IllegalArgumentException("이미 팔로우한 사람 입니다.");
        }
        account.getRank().upRankScore();
        final Follow follow = Follow.builder().followerId(account.getId()).followingId(following_id).build();
        followRepository.save(follow);
    }

    public void unFollow(Long unFollowing_id, Account account) {
        if (!followRepository.existsByFollowerIdAndFollowingId(account.getId(), unFollowing_id)) {
          throw new IllegalArgumentException("이미 언팔로우한 사람 입니다.");
        }
        account.getRank().downRankScore();
        followRepository.deleteByFollowerIdAndFollowingId(account.getId(),unFollowing_id);
    }

    //accountId를 팔로잉 하고있는 사람들의 리스트
    public List<FollowResponseDto.FollowList> findFollowingList(Long accountId) {
        return followRepository.findAllByFollowingId(accountId);
    }

    //accountId가 팔로잉 하고있는 사람들의 리스트
    public List<FollowResponseDto.FollowList> findFollowerList(Long accountId) {
        return followRepository.findAllByFollowerId(accountId);
    }

}
