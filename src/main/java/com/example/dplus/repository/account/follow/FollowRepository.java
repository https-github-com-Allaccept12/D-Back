package com.example.dplus.repository.account.follow;

import com.example.dplus.domain.account.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long>,FollowRepositoryCustom {

    Long countByFollowerId(Long followerId);
    Long countByFollowingId(Long followingId);
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
