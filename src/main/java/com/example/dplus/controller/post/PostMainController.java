package com.example.dplus.controller.post;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto;
import com.example.dplus.dto.request.PostRequestDto.PostCreate;
import com.example.dplus.dto.request.PostRequestDto.PostUpdate;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.PostMainPageService;
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

    private final PostMainPageService postMainPageService;

    // 디모 페이지 추천 피드
    @GetMapping("/recommend/{board}")
    public ResponseEntity<Success> postMain(@PathVariable String board) {
        return new ResponseEntity<>(new Success("디플 메인 페이지",
                postMainPageService.showPostRecommendation(board)), HttpStatus.OK);
    }
    // 카테고리별 최신순
    @GetMapping("/category/{category}/{last_post_id}/{board}")
    public ResponseEntity<Success> postMainByCategory(@PathVariable String category,
                                                      @PathVariable Long last_post_id,
                                                      @PathVariable String board) {
        return new ResponseEntity<>(new Success("카테고리별 메인 페이지",
                postMainPageService.showPostMain(last_post_id, board, category)), HttpStatus.OK);
    }

    // 카테고리별 좋아요
    @GetMapping("/category/like/{category}/{board}")
    public ResponseEntity<Success> postSortByCategory(@RequestParam("start") int start,
                                                      @PathVariable String board,
                                                      @PathVariable String category) {
        return new ResponseEntity<>(new Success("카테고리별 좋아요순 디플페이지",
                postMainPageService.showPostMainLikeSort(start, board, category)), HttpStatus.OK);
    }

    // 상세
    @GetMapping("/{post_id}")
    public ResponseEntity<Success> postDetail(@RequestParam(value = "visitor_account_id",required = false) Long user,
                                              @PathVariable Long post_id) {
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("디플 상세 페이지",
                postMainPageService.showPostDetail(accountId, post_id)), HttpStatus.OK);
    }
    // 게시물 등록
    @PostMapping("")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestPart PostCreate data,
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
                                              @RequestPart PostUpdate data,
                                              @RequestPart(required = false) List<MultipartFile> imgFile) {

        if (user != null) {
            return new ResponseEntity<>(new Success("디플 게시물 수정",
                    postMainPageService.updatePost(user.getUser().getId(), post_id, data, imgFile)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    // 게시물 삭제
    @PatchMapping("/del")
    public ResponseEntity<Success> deletePost(@AuthenticationPrincipal UserDetailsImpl user,
                                              @RequestBody CommonDto.Id postId,
                                              @RequestParam("board") String board) {
        if (user != null) {
            postMainPageService.deletePost(user.getUser().getId(), postId.getId(), board);
            return new ResponseEntity<>(new Success("디플 게시물 삭제", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }


    // 게시물 검색
    @GetMapping("/search/{last_post_id}/{board}/{keyword}")
    public ResponseEntity<Success> postSearch(@PathVariable Long last_post_id,
                                              @PathVariable String board,
                                              @PathVariable String keyword) {
        if (keyword == null) {
            throw new ErrorCustomException(ErrorCode.NON_KEYWORD_ERROR);
        }
        return new ResponseEntity<>(new Success("게시물 검색 완료",
                postMainPageService.findBySearchKeyWord(keyword, last_post_id, board)), HttpStatus.OK);

    }

    // 질문글 상세 조회
    @GetMapping("/question/{post_id}")
    public ResponseEntity<Success> postQuestionDetail (@RequestParam(value = "visitor_account_id",required = false) Long user,
                                                       @PathVariable Long post_id){
        Long accountId = getaLong(user);
        return new ResponseEntity<>(new Success("디플 질문 상세페이지 조회",
                postMainPageService.detailAnswer(accountId, post_id)), HttpStatus.OK);
    }

    // 유사한 질문 조회
    @GetMapping("/question/similar/{category}/{post_id}")
    public ResponseEntity<Success> similarQuestion (@PathVariable String category,
                                                    @PathVariable Long post_id){
        return new ResponseEntity<>(new Success("유사한 질문 리스트",
                postMainPageService.similarQuestion(category, post_id)), HttpStatus.OK);

    }
    private Long getaLong(Long user) {
        long accountId = 0L ;
        if (user != null) {
            accountId = user;
        }
        return accountId;
    }
}
