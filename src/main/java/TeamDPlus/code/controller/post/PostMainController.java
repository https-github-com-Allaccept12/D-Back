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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostMainController {

    private final PostMainPageService postMainPageService;

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
                postMainPageService.showPostMainByLikes(user.getUser().getId(), last_post_id, board)), HttpStatus.OK);
    }

    // 상세 목록
    @GetMapping("/{post_id}")
    public ResponseEntity<Success> postDetail(@AuthenticationPrincipal UserDetailsImpl user,
                                        @PathVariable Long post_id) {
        return new ResponseEntity<>(new Success("디플 상세 페이지",
                postMainPageService.showPostDetail(user.getUser().getId(), post_id)), HttpStatus.OK);
    }

    // 게시물 등록 - dto에서 파일명이랑 썸네일 boolean 값
    @PostMapping("")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestPart PostRequestDto.PostCreate data,
                                              @RequestPart List<MultipartFile> imgFile) {
        System.out.println(user.getUser());
        return new ResponseEntity<>(new Success("디플 게시물 등록",
                postMainPageService.createPost(user.getUser(), data, imgFile)), HttpStatus.OK);
    }

    // 게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<Success> updatePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id,
                                              @RequestPart PostRequestDto.PostUpdate data,
                                              @RequestPart List<MultipartFile> imgFile) {
        return new ResponseEntity<>(new Success("디플 게시물 수정",
                postMainPageService.updatePost(user.getUser(), post_id, data, imgFile)), HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> deletePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id) {
        postMainPageService.deletePost(user.getUser().getId(), post_id);
        return new ResponseEntity<>(new Success("디플 게시물 삭제",""), HttpStatus.OK);
    }

    // 질문글 상세 조회
    @PostMapping("/question/{post_id}")
    public ResponseEntity<Success> postQuestionDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long post_id) {
        return new ResponseEntity<>(new Success("디플 질문 상세페이지 조회",
                postMainPageService.detailAnswer(userDetails.getUser().getId(), post_id)), HttpStatus.OK);
    }

    // 유사한 질문 조회
    @GetMapping("/question/similar/{category}")
    public ResponseEntity<Success> similarQuestion(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable String category) {
        return new ResponseEntity<>(new Success("유사한 질문 리스트",
                postMainPageService.similarQuestion(category, userDetails.getUser().getId())), HttpStatus.OK);
    }

}
