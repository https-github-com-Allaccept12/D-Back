package com.example.dplus.controller.post;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.bookmark.PostBookMarkService;
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

    @PostMapping("/post")
    public ResponseEntity<Success> doBookmark(@RequestBody Id postId,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postBookMarkService.doBookMark(user.getUser(), postId.getId());
            return new ResponseEntity<>(new Success("게시글 북마크 성공", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/post")
    public ResponseEntity<Success> unBookmark(@RequestBody Id postId,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            postBookMarkService.unBookMark(user.getUser(), postId.getId());
            return new ResponseEntity<>(new Success("게시글 북마크 해지", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

}
