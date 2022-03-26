package com.example.TeamDPlus.controller.post;

import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.dto.Success;
import com.example.TeamDPlus.jwt.UserDetailsImpl;
import com.example.TeamDPlus.service.post.comment.like.PostCommentLikeService;
import com.example.TeamDPlus.service.post.like.PostAnswerLikeService;
import com.example.TeamDPlus.service.post.like.PostLikeService;
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

    @PostMapping("/{post_id}")
    public ResponseEntity<Success> doLike(@PathVariable Long post_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postLikeService.doLike(user.getUser(), post_id);
            return new ResponseEntity<>(new Success("작품 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> unLike(@PathVariable Long post_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postLikeService.unLike(user.getUser(), post_id);
            return new ResponseEntity<>(new Success("작품 좋아요 취소", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    // 코멘트에 대한 like 처리.. postId를 따로 찾지 않아도 되지 않을까.. seq베이스면?
    @PostMapping("/{postComment_id}")
    public ResponseEntity<Success> commentDoLike(@PathVariable Long postComment_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postCommentLikeService.doLike(user.getUser(), postComment_id);
            return new ResponseEntity<>(new Success("작품 코멘트 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/{postComment_id}")
    public ResponseEntity<Success> commentUnLike(@PathVariable Long postComment_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postCommentLikeService.unLike(user.getUser(), postComment_id);
            return new ResponseEntity<>(new Success("작품 코멘트 좋아요 취소", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PostMapping("/answer/{postAnswer_id}")
    public ResponseEntity<Success> answerDoLike(@PathVariable Long postAnswer_id,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postAnswerLikeService.answerDoLike(user.getUser(), postAnswer_id);
            return new ResponseEntity<>(new Success("작품 QnA 답글 좋아요 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/answer/{postAnswer_id}")
    public ResponseEntity<Success> answerUnLike(@PathVariable Long postAnswer_id,
                                                @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postAnswerLikeService.answerUnLike(user.getUser(), postAnswer_id);
            return new ResponseEntity<>(new Success("작품 QnA 답글 좋아요 취소 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
}
