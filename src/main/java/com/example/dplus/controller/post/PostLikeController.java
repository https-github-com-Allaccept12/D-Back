package com.example.dplus.controller.post;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.comment.like.PostCommentLikeService;
import com.example.dplus.service.post.like.PostAnswerLikeService;
import com.example.dplus.service.post.like.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/like")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final PostCommentLikeService postCommentLikeService;
    private final PostAnswerLikeService postAnswerLikeService;

    @PostMapping("")
    public ResponseEntity<Success> doLike(@RequestBody Id postId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postLikeService.doLike(user.getUser(), postId.getId());
            return new ResponseEntity<>(new Success("게시물 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("")
    public ResponseEntity<Success> unLike(@RequestBody Id postId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postLikeService.unLike(user.getUser(), postId.getId());
            return new ResponseEntity<>(new Success("게시물 좋아요 취소", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    // 코멘트에 대한 like 처리.. postId를 따로 찾지 않아도 되지 않을까.. seq베이스면?
    @PostMapping("/comment")
    public ResponseEntity<Success> commentDoLike(@RequestBody Id postCommentId,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postCommentLikeService.doLike(user.getUser(), postCommentId.getId());
            return new ResponseEntity<>(new Success("게시물 코멘트 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/comment")
    public ResponseEntity<Success> commentUnLike(@RequestBody Id postCommentId,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postCommentLikeService.unLike(user.getUser(), postCommentId.getId());
            return new ResponseEntity<>(new Success("게시물 코멘트 좋아요 취소", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PostMapping("/answer")
    public ResponseEntity<Success> answerDoLike(@RequestBody Id postAnswerId,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postAnswerLikeService.answerDoLike(user.getUser(), postAnswerId.getId());
            return new ResponseEntity<>(new Success("게시물 QnA 답글 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/answer")
    public ResponseEntity<Success> answerUnLike(@RequestBody Id postAnswerId,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postAnswerLikeService.answerUnLike(user.getUser(), postAnswerId.getId());
            return new ResponseEntity<>(new Success("게시물 QnA 답글 좋아요 취소 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
}