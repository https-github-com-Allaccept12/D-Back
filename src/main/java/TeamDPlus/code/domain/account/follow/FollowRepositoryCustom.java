package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.dto.response.FollowResponseDto;
import TeamDPlus.code.dto.response.FollowResponseDto.FollowList;

import java.util.List;

public interface FollowRepositoryCustom {

    //followingId 을 팔로잉 하고있는 사람을 다찾기
    List<FollowList> findAllByFollowingId(Long followingId);

    //followerId 이 팔로잉 하고있는 사람을 다찾기
    List<FollowList> findAllByFollowerId(Long followerId);

    boolean existsByFollowerIdAndFollowingId(Long accountId,Long followingId);

}
