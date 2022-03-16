package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.artwork.ArtworkMainService;
import TeamDPlus.code.service.artwork.bookmark.ArtWorkBookMarkService;
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
        artWorkBookMarkService.doBookMark(user.getUser(),artwork_id);
        return new ResponseEntity<>(new Success("작품 북마크 성공",""), HttpStatus.OK);
    }
    @DeleteMapping("/artwork/{artwork_id}")
    public ResponseEntity<Success> unBookmark(@PathVariable Long artwork_id,
                                            @AuthenticationPrincipal UserDetailsImpl user) {
        artWorkBookMarkService.unBookMark(user.getUser(),artwork_id);
        return new ResponseEntity<>(new Success("작품 북마크 해지",""), HttpStatus.OK);
    }



}
