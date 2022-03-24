package TeamDPlus.code.controller.post;


import TeamDPlus.code.domain.post.PostBoard;
import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.post.PostMainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Slf4j
public class PostMainController {

    private final int SORT_SIGN_LATEST = 1;
    private final PostMainPageService postMainPageService;

    // 전체 목록
    @GetMapping("/{last_post_id}/{board}")
    public ResponseEntity<Success> postMain(@AuthenticationPrincipal UserDetailsImpl user,
                                            @PathVariable Long last_post_id,
                                            @PathVariable PostBoard board) {

        if (user == null) {
            return new ResponseEntity<>(new Success("디플 메인 페이지",
                    postMainPageService.showPostMain(null, last_post_id, board, "", SORT_SIGN_LATEST)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Success("디플 메인 페이지",
                postMainPageService.showPostMain(user.getUser().getId(), last_post_id, board, "", SORT_SIGN_LATEST)), HttpStatus.OK);
    }

    // 전체 목록 (카테고리별)
    @GetMapping("/category/{category}/{last_post_id}/{board}")
    public ResponseEntity<Success> postMainByCategory(@AuthenticationPrincipal UserDetailsImpl user,
                                                      @PathVariable String category,
                                                      @PathVariable Long last_post_id,
                                                      @PathVariable PostBoard board) {

        if (user == null) {
            return new ResponseEntity<>(new Success("카테고리별 메인 페이지",
                    postMainPageService.showPostMain(null, last_post_id, board, category, SORT_SIGN_LATEST)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Success("카테고리별 메인 페이지",
                postMainPageService.showPostMain(user.getUser().getId(), last_post_id, board, category, SORT_SIGN_LATEST)), HttpStatus.OK);
    }

    // 카테고리별 정렬
    @GetMapping("/sort/{category}/{sortsign}/{last_post_id}/{board}")
    public ResponseEntity<Success> postSortByCatetory(@AuthenticationPrincipal UserDetailsImpl user,
                                                      @PathVariable int sortsign,
                                                      @PathVariable Long last_post_id,
                                                      @PathVariable PostBoard board,
                                                      @PathVariable String category) {
        if (user == null) {
            return new ResponseEntity<>(new Success("카테고리별 정렬한 디플페이지",
                    postMainPageService.showPostMain(null, last_post_id, board, category, sortsign)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("카테고리별 정렬한 디플페이지",
                postMainPageService.showPostMain(user.getUser().getId(), last_post_id, board, category, sortsign)), HttpStatus.OK);
    }

    // 상세 목록
    @GetMapping("/{post_id}")
    public ResponseEntity<Success> postDetail(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id) {
        if (user == null) {
            return new ResponseEntity<>(new Success("디플 상세 페이지",
                    postMainPageService.showPostDetail(null, post_id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("디플 상세 페이지",
                postMainPageService.showPostDetail(user.getUser().getId(), post_id)), HttpStatus.OK);
    }

    // 게시물 등록
    @PostMapping("")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestPart PostRequestDto.PostCreate data,
                                              @RequestPart List<MultipartFile> imgFile) {

        return new ResponseEntity<>(new Success("디플 게시물 등록",
                postMainPageService.createPost(user.getUser(), data, imgFile)), HttpStatus.OK);
    }

    // 게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<Success> updatePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id,
                                              @RequestPart PostRequestDto.PostUpdate data,
                                              @RequestPart(value = "file", required = false) List<MultipartFile> imgFile) {
        return new ResponseEntity<>(new Success("디플 게시물 수정",
                postMainPageService.updatePost(user.getUser(), post_id, data, imgFile)), HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> deletePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id) {
        postMainPageService.deletePost(user.getUser().getId(), post_id);
        return new ResponseEntity<>(new Success("디플 게시물 삭제", ""), HttpStatus.OK);
    }


    // 게시물 검색
    @GetMapping("/search/{last_post_id}/{board}/{keyword}")
    public ResponseEntity<Success> postSearch(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long last_post_id,
                                              @PathVariable PostBoard board,
                                              @PathVariable String keyword) {
        if (keyword == null) {
            throw new IllegalStateException("검색어를 입력 해주세요.");
        }
        if (user == null) {
            return new ResponseEntity<>(new Success("작품 검색 완료",
                    postMainPageService.findBySearchKeyWord(keyword, last_post_id, null, board)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Success("작품 검색 완료",
                postMainPageService.findBySearchKeyWord(keyword, last_post_id, user.getUser().getId(), board)), HttpStatus.OK);

    }

    // 질문글 상세 조회
    @GetMapping("/question/{post_id}")
    public ResponseEntity<Success> postQuestionDetail (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable Long post_id){
        return new ResponseEntity<>(new Success("디플 질문 상세페이지 조회",
                postMainPageService.detailAnswer(userDetails.getUser().getId(), post_id)), HttpStatus.OK);
    }

    // 유사한 질문 조회
    @GetMapping("/question/similar/{category}")
    public ResponseEntity<Success> similarQuestion (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable String category){
        return new ResponseEntity<>(new Success("유사한 질문 리스트",
                postMainPageService.similarQuestion(category, userDetails.getUser().getId())), HttpStatus.OK);

    }
}
