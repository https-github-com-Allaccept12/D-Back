package com.example.dplus.controller.artwork;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.common.CommonDto.Id;
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

    @PostMapping("/artwork")
    public ResponseEntity<Success> doBookmark(@RequestBody Id artWorkId,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artWorkBookMarkService.doBookMark(user.getUser(), artWorkId.getId());
            return new ResponseEntity<>(new Success("작품 북마크 성공",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);

    }
    @PatchMapping("/artwork")
    public ResponseEntity<Success> unBookmark(@RequestBody Id artWorkId,
                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (user != null) {
            artWorkBookMarkService.unBookMark(user.getUser(),artWorkId.getId());
            return new ResponseEntity<>(new Success("작품 북마크 해지",""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }


}
