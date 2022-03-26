package com.example.TeamDPlus.domain.account.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long>,FollowRepositoryCustom {

    Long countByFollowerId(Long followerId);
    Long countByFollowingId(Long followingId);
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
