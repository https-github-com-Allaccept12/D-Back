package com.example.dplus.controller.post;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.comment.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/comment/{post_id}")
    public ResponseEntity<Success> createPostComment(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long post_id,
                                                     @Valid @RequestBody PostRequestDto.PostComment data) {
        if (user != null) {
            return new ResponseEntity<>(new Success("게시글 코멘트 등록 완료",
                    postCommentService.createComment(user.getUser(), post_id, data)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/comment/{post_comment_id}")
    public ResponseEntity<Success> updatePostComment(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long post_comment_id,
                                                     @Valid @RequestBody PostRequestDto.PostComment data) {
        if (user != null) {
            return new ResponseEntity<>(new Success("게시글 코멘트 수정 완료",
                    postCommentService.updateComment(user.getUser().getId(), post_comment_id, data)), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/comment/del")
    public ResponseEntity<Success> deletePostComment(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @RequestBody CommonDto.Id postCommentId) {
        if (user != null) {
            postCommentService.deleteComment(user.getUser().getId(), postCommentId.getId());
            return new ResponseEntity<>(new Success("게시글 코멘트 삭제 완료",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
}