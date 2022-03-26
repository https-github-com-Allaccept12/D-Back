package com.example.dplus.controller.post;

import com.example.dplus.dto.Success;
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

    @PostMapping("/{post_id}")
    public ResponseEntity<Success> doLike(@PathVariable Long post_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        postLikeService.doLike(user.getUser(), post_id);
        return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Success> unLike(@PathVariable Long post_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        postLikeService.unLike(user.getUser(), post_id);
        return new ResponseEntity<>(new Success("작품 좋아요 취소",""), HttpStatus.OK);
    }

    // 코멘트에 대한 like 처리.. postId를 따로 찾지 않아도 되지 않을까.. seq베이스면?
    @PostMapping("/comment/{postComment_id}")
    public ResponseEntity<Success> commentDoLike(@PathVariable Long postComment_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        postCommentLikeService.doLike(user.getUser(), postComment_id);
        return new ResponseEntity<>(new Success("작품 코멘트 좋아요 완료",""), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{postComment_id}")
    public ResponseEntity<Success> commentUnLike(@PathVariable Long postComment_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        postCommentLikeService.unLike(user.getUser(), postComment_id);
        return new ResponseEntity<>(new Success("작품 코멘트 좋아요 취소",""), HttpStatus.OK);
    }

    @PostMapping("/answer/{postAnswer_id}")
    public ResponseEntity<Success> answerDoLike(@PathVariable Long postAnswer_id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postAnswerLikeService.answerDoLike(userDetails.getUser(), postAnswer_id);
        return new ResponseEntity<>(new Success("작품 QnA 답글 좋아요 완료",""), HttpStatus.OK);
    }

    @DeleteMapping("/answer/{postAnswer_id}")
    public ResponseEntity<Success> answerUnLike(@PathVariable Long postAnswer_id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postAnswerLikeService.answerUnLike(userDetails.getUser(), postAnswer_id);
        return new ResponseEntity<>(new Success("작품 QnA 답글 좋아요 취소 완료",""), HttpStatus.OK);
    }
}
