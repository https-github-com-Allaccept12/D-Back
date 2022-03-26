package com.example.dplus.controller.artwork;


import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.dto.Success;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.artwork.like.ArtworkLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artwork/like")
public class ArtWorkLikeController {

    private final ArtworkLikeService artworkLikeService;

    @PostMapping("/{artwork_id}")
    public ResponseEntity<Success> doLike(@PathVariable Long artwork_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artworkLikeService.doLike(user.getUser(),artwork_id);
            return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
    @DeleteMapping("/{artwork_id}")
    public ResponseEntity<Success> unLike(@PathVariable Long artwork_id,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artworkLikeService.unLike(user.getUser(),artwork_id);
            return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }


}