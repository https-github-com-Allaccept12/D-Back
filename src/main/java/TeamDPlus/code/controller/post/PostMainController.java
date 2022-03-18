package TeamDPlus.code.controller.post;


import TeamDPlus.code.domain.post.PostBoard;
import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.post.PostMainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostMainController {

    private PostMainPageService postMainPageService;

    // 전체 목록
    @GetMapping("/{last_post_id}/{board}/latest")
    public ResponseEntity<Success> postMainLatest(@AuthenticationPrincipal UserDetailsImpl user,
                                        @PathVariable Long last_post_id,
                                        @PathVariable PostBoard board) {
        return new ResponseEntity<>(new Success("디플 메인 페이지",
                postMainPageService.showPostMain(user.getUser().getId(), last_post_id, board)), HttpStatus.OK);
    }

    @GetMapping("/{last_post_id}/{board}/likes")
    public ResponseEntity<Success> postMainLikes(@AuthenticationPrincipal UserDetailsImpl user,
                                            @PathVariable Long last_post_id,
                                            @PathVariable PostBoard board) {
        return new ResponseEntity<>(new Success("디플 메인 페이지",
                postMainPageService.showPostMain(user.getUser().getId(), last_post_id, board)), HttpStatus.OK);
    }

    // 상세 목록
    @GetMapping("/{post_id}")
    public ResponseEntity<Success> postDetail(@AuthenticationPrincipal UserDetailsImpl user,
                                        @PathVariable Long post_id) {
        return new ResponseEntity<>(new Success("디플 상세 페이지",
                postMainPageService.showPostDetail(user.getUser().getId(), post_id)), HttpStatus.OK);
    }

    // 게시물 등록
    @PostMapping("")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody PostRequestDto.PostCreate data) {
        return new ResponseEntity<>(new Success("디플 게시물 등록",
                postMainPageService.createPost(user.getUser(), data)), HttpStatus.OK);
    }

    // 게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<Success> updatePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id,
                                              @RequestBody PostRequestDto.PostUpdate data) {
        return new ResponseEntity<>(new Success("디플 게시물 수정",
                postMainPageService.updatePost(user.getUser(), post_id, data)), HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> deletePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id) {
        postMainPageService.deletePost(user.getUser().getId(), post_id);
        return new ResponseEntity<>(new Success("디플 게시물 삭제",""), HttpStatus.OK);
    }

}
