package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
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

    @PostMapping("")
    public ResponseEntity<Success> doLike(@RequestBody Id artworkId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artworkLikeService.doLike(user.getUser(),artworkId.getId());
            return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }
    @PatchMapping("")
    public ResponseEntity<Success> unLike(@RequestBody Id artworkId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artworkLikeService.unLike(user.getUser(), artworkId.getId());
            return new ResponseEntity<>(new Success("작품 좋아요 해지", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }

}
