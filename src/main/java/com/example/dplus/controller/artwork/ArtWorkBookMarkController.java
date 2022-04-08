package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.artwork.bookmark.ArtWorkBookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class ArtWorkBookMarkController {

    private final ArtWorkBookMarkService artWorkBookMarkService;

    @PostMapping("/artwork/{artwork_id}")
    public ResponseEntity<Success> doBookmark(@PathVariable Long artwork_id,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artWorkBookMarkService.doBookMark(user.getUser(),artwork_id);
            return new ResponseEntity<>(new Success("작품 북마크 성공",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }
    @PatchMapping("/artwork/{artwork_id}")
    public ResponseEntity<Success> unBookmark(@PathVariable Long artwork_id,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artWorkBookMarkService.unBookMark(user.getUser(),artwork_id);
            return new ResponseEntity<>(new Success("작품 북마크 해지",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }


}
