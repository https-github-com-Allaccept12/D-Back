package com.example.dplus.controller.account;


import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.account.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    //팔로우
    @PostMapping("")
    public ResponseEntity<Success> doFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestBody Id accountId) {
        followService.follow(accountId.getId(),user.getUser().getId());
        return new ResponseEntity<>(new Success("팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //언팔로우
    @PatchMapping("")
    public ResponseEntity<Success> unFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestBody Id accountId) {
        followService.unFollow(accountId.getId(),user.getUser().getId());
        return new ResponseEntity<>(new Success("언 팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //팔로잉리스트
    @GetMapping("/following")
    public ResponseEntity<Success> followerList(@RequestParam("account_id") Long accountId) {
        return new ResponseEntity<>(new Success("팔로워 리스트",
                followService.findFollowingList(accountId)),HttpStatus.OK);
    }
    //팔로워리스트
    @GetMapping("/follower")
    public ResponseEntity<Success> followingList(@RequestParam("account_id") Long accountId) {
        return new ResponseEntity<>(new Success("팔로잉 리스트",
                followService.findFollowerList(accountId)),HttpStatus.OK);
    }
}








