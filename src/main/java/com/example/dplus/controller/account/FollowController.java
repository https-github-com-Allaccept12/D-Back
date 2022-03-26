package com.example.dplus.controller.account;


import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.AccountRequestDto.AccountVisit;
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
                                            @RequestBody AccountVisit accountId) {
        followService.follow(accountId.getAccount_id(),user.getUser().getId());
        return new ResponseEntity<>(new Success("팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //언팔로우
    @DeleteMapping("")
    public ResponseEntity<Success> unFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestBody AccountVisit accountId) {
        followService.unFollow(accountId.getAccount_id(),user.getUser().getId());
        return new ResponseEntity<>(new Success("언 팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //팔로워리스트
    @GetMapping("/follower")
    public ResponseEntity<Success> followerList(@RequestBody AccountVisit accountId) {
        return new ResponseEntity<>(new Success("팔로워 리스트",
                followService.findFollowingList(accountId.getAccount_id())),HttpStatus.OK);
    }
    //팔로잉리스트
    @GetMapping("/following")
    public ResponseEntity<Success> followingList(@RequestBody AccountVisit accountId) {
        return new ResponseEntity<>(new Success("팔로잉 리스트",
                followService.findFollowerList(accountId.getAccount_id())),HttpStatus.OK);
    }
}








