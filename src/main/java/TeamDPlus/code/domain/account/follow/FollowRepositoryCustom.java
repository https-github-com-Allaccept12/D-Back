package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.dto.response.FollowResponseDto;

import java.util.List;

public interface FollowRepositoryCustom {

    //followingId 을 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowingId(Long followingId);

    //followerId 이 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowerId(Long followerId);

    boolean existsByFollowerIdAndAndFollowingId(Long accountId,Long followingId);
}
