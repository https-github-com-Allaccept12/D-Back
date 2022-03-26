package com.example.dplus.domain.account.follow;

import com.example.dplus.dto.response.FollowResponseDto;

import java.util.List;

public interface FollowRepositoryCustom {

    //followingId 을 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowingId(Long followingId);

    //followerId 이 팔로잉 하고있는 사람을 다찾기
    List<FollowResponseDto.FollowList> findAllByFollowerId(Long followerId);

    boolean existsByFollowerIdAndFollowingId(Long accountId,Long followingId);

}
