package com.example.dplus.service.account.follow;


import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.AccountRepository;
import com.example.dplus.domain.account.follow.Follow;
import com.example.dplus.domain.account.follow.FollowRepository;
import com.example.dplus.dto.response.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void follow(Long following_id, Long accountId) {
        Account account = getAccount(accountId);
        if (followRepository.existsByFollowerIdAndFollowingId(account.getId(),following_id)) {
            throw new ApiRequestException(ErrorCode.EXIST_FOLLOW_ERROR);
        }
        account.getRank().upRankScore();
        final Follow follow = Follow.builder().followerId(account.getId()).followingId(following_id).build();
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(Long unFollowing_id, Long accountId) {
        Account account = getAccount(accountId);
        if (!followRepository.existsByFollowerIdAndFollowingId(account.getId(), unFollowing_id)) {
          throw new ApiRequestException(ErrorCode.EXIST_FOLLOW_ERROR);
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
        return accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));
    }

}