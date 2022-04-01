package com.example.dplus.repository.account.follow;

import com.example.dplus.dto.response.FollowResponseDto.FollowList;

import java.util.List;

public interface FollowRepositoryCustom {

    //followingId 을 팔로우 하고있는 사람을 다찾기
    List<FollowList> findAllByFollowingId(Long followingId);

    //followerId 이 팔로우 하고있는 사람을 다찾기
    List<FollowList> findAllByFollowerId(Long followerId);

    boolean existsByFollowerIdAndFollowingId(Long accountId,Long followingId);

}
