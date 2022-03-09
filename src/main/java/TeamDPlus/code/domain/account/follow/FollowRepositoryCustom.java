package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.dto.response.FollowResponseDto;

import java.util.List;

public interface FollowRepositoryCustom {

    //이사람을 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowingId(Long followingId);

    //이사람이 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowerId(Long followerId);
}