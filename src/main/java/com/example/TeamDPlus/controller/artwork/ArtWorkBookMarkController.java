package com.example.TeamDPlus.controller.artwork;


import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.dto.Success;
import com.example.TeamDPlus.jwt.UserDetailsImpl;
import com.example.TeamDPlus.service.artwork.bookmark.ArtWorkBookMarkService;
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
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }
    @DeleteMapping("/artwork/{artwork_id}")
    public ResponseEntity<Success> unBookmark(@PathVariable Long artwork_id,
                                            @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artWorkBookMarkService.unBookMark(user.getUser(),artwork_id);
            return new ResponseEntity<>(new Success("작품 북마크 해지",""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }



}
