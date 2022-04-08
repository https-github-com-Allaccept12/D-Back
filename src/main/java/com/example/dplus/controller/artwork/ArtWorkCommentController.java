package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkComment;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.artwork.comment.ArtWorkCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artwork/comment")
public class ArtWorkCommentController {
    private final ArtWorkCommentService artWorkCommentService;

    @PostMapping("/{artwork_id}")
    public ResponseEntity<Success> createComment(@PathVariable Long artwork_id,
                                                 @Valid @RequestBody ArtWorkComment data,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            return new ResponseEntity<>(new Success("코멘트 등록 완료",
                    artWorkCommentService.createComment(data,artwork_id,user.getUser())), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
    @PatchMapping("/{comment_id}")
    public ResponseEntity<Success> updateComment(@PathVariable Long comment_id,
                                                 @Valid @RequestBody ArtWorkComment data,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        artWorkCommentService.updateComment(comment_id,data,user.getUser().getId());
        return new ResponseEntity<>(new Success("코멘트 수정 완료","" ), HttpStatus.OK);
    }
    @PatchMapping("/del/{comment_id}")
    public ResponseEntity<Success> updateComment(@PathVariable Long comment_id,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        artWorkCommentService.deleteComment(comment_id,user.getUser().getId());
        return new ResponseEntity<>(new Success("코멘트 삭제 완료","" ), HttpStatus.OK);
    }
}
