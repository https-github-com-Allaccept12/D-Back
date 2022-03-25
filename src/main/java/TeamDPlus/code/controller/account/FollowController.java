package TeamDPlus.code.controller.account;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.account.follow.FollowService;
import lombok.Getter;
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
                                            @RequestParam("account_id") Long account_id) {
        followService.follow(account_id,user.getUser().getId());
        return new ResponseEntity<>(new Success("팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //언팔로우
    @DeleteMapping("")
    public ResponseEntity<Success> unFollow(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestParam("account_id") Long account_id) {
        followService.unFollow(account_id,user.getUser().getId());
        return new ResponseEntity<>(new Success("언 팔로우 했습니다.", ""), HttpStatus.OK);
    }
    //팔로워리스트
    @GetMapping("/follower")
    public ResponseEntity<Success> followerList(@RequestParam("account_id") Long account_id) {
        return new ResponseEntity<>(new Success("팔로워 리스트",
                followService.findFollowingList(account_id)),HttpStatus.OK);
    }
    //팔로잉리스트
    @GetMapping("/following")
    public ResponseEntity<Success> followingList(@RequestParam("account_id") Long account_id) {
        return new ResponseEntity<>(new Success("팔로잉 리스트",
                followService.findFollowerList(account_id)),HttpStatus.OK);
    }
}








