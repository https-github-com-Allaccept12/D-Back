package com.example.dplus.controller.post;



import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;

import com.example.dplus.advice.ErrorCode;

import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.PostMainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    public ResponseEntity<Success> postMain(@RequestParam("account_id") Long account_id,
                                            @PathVariable Long last_post_id,
                                            @PathVariable String board) {

            return new ResponseEntity<>(new Success("디플 메인 페이지",
                    postMainPageService.showPostMain(account_id, last_post_id, board, "", SORT_SIGN_LATEST)), HttpStatus.OK);
        }

    // 전체 목록 (카테고리별)
    @GetMapping("/category/{category}/{last_post_id}/{board}")
    public ResponseEntity<Success> postMainByCategory(@RequestParam("account_id") Long account_id,
                                                      @PathVariable String category,
                                                      @PathVariable Long last_post_id,
                                                      @PathVariable String board) {

        return new ResponseEntity<>(new Success("카테고리별 메인 페이지",
                postMainPageService.showPostMain(account_id, last_post_id, board, category, SORT_SIGN_LATEST)), HttpStatus.OK);
    }

    // 카테고리별 정렬 + 좋아요
    @GetMapping("/sort/{category}/{sortsign}/{last_post_id}/{board}")
    public ResponseEntity<Success> postSortByCatetory(@RequestParam("account_id") Long account_id,
                                                      @PathVariable int sortsign,
                                                      @PathVariable Long last_post_id,
                                                      @PathVariable String board,
                                                      @PathVariable String category) {

        return new ResponseEntity<>(new Success("카테고리별 정렬한 디플페이지",
                postMainPageService.showPostMain(account_id, last_post_id, board, category, sortsign)), HttpStatus.OK);
    }

    // 상세 목록
    @GetMapping("/{post_id}")
    public ResponseEntity<Success> postDetail(@RequestParam(value = "account_id", required = false) Long account_id,
                                              @PathVariable Long post_id) {
        return new ResponseEntity<>(new Success("디플 상세 페이지",
                postMainPageService.showPostDetail(account_id, post_id)), HttpStatus.OK);
    }

    // 게시물 등록
    @PostMapping("")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestPart PostRequestDto.PostCreate data,
                                              @RequestPart(required = false) List<MultipartFile> imgFile) {

        if (user != null) {
            return new ResponseEntity<>(new Success("디플 게시물 등록",
                    postMainPageService.createPost(user.getUser().getId(), data, imgFile)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    // 게시물 수정
    @PatchMapping("/{post_id}")
    public ResponseEntity<Success> updatePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id,
                                              @Valid @RequestPart PostRequestDto.PostUpdate data,
                                              @RequestPart(required = false) List<MultipartFile> imgFile) {

        if (user != null) {
            return new ResponseEntity<>(new Success("디플 게시물 수정",
                    postMainPageService.updatePost(user.getUser(), post_id, data, imgFile)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    // 게시물 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> deletePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @PathVariable Long post_id) {
        if (user != null) {
            postMainPageService.deletePost(user.getUser().getId(), post_id);
            return new ResponseEntity<>(new Success("디플 게시물 삭제", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }


    // 게시물 검색
    @GetMapping("/search/{last_post_id}/{board}/{keyword}")
    public ResponseEntity<Success> postSearch(@RequestParam("account_id") Long account_id,
                                              @PathVariable Long last_post_id,
                                              @PathVariable String board,
                                              @PathVariable String keyword) {
        if (keyword == null) {
            throw new ErrorCustomException(ErrorCode.NON_KEYWORD_ERROR);
        }
        return new ResponseEntity<>(new Success("게시물 검색 완료",
                postMainPageService.findBySearchKeyWord(keyword, last_post_id, account_id, board)), HttpStatus.OK);

    }

    // 질문글 상세 조회
    @GetMapping("/question/{post_id}")
    public ResponseEntity<Success> postQuestionDetail (@RequestParam("account_id") Long account_id,
                                                       @PathVariable Long post_id){
        return new ResponseEntity<>(new Success("디플 질문 상세페이지 조회",
                postMainPageService.detailAnswer(account_id, post_id)), HttpStatus.OK);
    }

    // 유사한 질문 조회
    @GetMapping("/question/similar/{category}/{post_id}")
    public ResponseEntity<Success> similarQuestion (@RequestParam("account_id") Long account_id,
                                                    @PathVariable String category,
                                                    @PathVariable Long post_id){
        return new ResponseEntity<>(new Success("유사한 질문 리스트",
                postMainPageService.similarQuestion(category, account_id, post_id)), HttpStatus.OK);

    }
}
