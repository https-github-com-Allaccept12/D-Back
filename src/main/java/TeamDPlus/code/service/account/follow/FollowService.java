package TeamDPlus.code.service.account.follow;


import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.Follow;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {


    private final FollowRepository followRepository;

    private final AccountRepository accountRepository;

    public void follow(Long following_id, Long account_id) {
        if (followRepository.existsByFollowerIdAndAndFollowingId(account_id,following_id)) {
            throw new IllegalArgumentException("이미 팔로우한 사람 입니다.");
        }
        final Follow follow = Follow.builder().followerId(account_id).followingId(following_id).build();
        followRepository.save(follow);
    }

    public void unFollow(Long unFollowing_id, Long account_id) {
        if (!followRepository.existsByFollowerIdAndAndFollowingId(account_id, unFollowing_id)) {
          throw new IllegalArgumentException("이미 언팔로우한 사람 입니다.");
        }
        followRepository.deleteByFollowerIdAndFollowingId(account_id,unFollowing_id);
    }

}
