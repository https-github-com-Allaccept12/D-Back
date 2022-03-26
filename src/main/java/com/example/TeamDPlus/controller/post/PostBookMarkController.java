package com.example.TeamDPlus.controller.post;

import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.dto.Success;
import com.example.TeamDPlus.jwt.UserDetailsImpl;
import com.example.TeamDPlus.service.post.bookmark.PostBookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class PostBookMarkController {

    private final PostBookMarkService postBookMarkService;

    @PostMapping("/post/{post_id}")
    public ResponseEntity<Success> doBookmark(@PathVariable Long post_id,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postBookMarkService.doBookMark(user.getUser(), post_id);
            return new ResponseEntity<>(new Success("게시글 북마크 성공", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<Success> unBookmark(@PathVariable Long post_id,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postBookMarkService.unBookMark(user.getUser(), post_id);
            return new ResponseEntity<>(new Success("게시글 북마크 해지", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

}
