package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.AccountRequestDto.AccountVisit;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.artwork.like.ArtworkLikeService;
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
                                          //@RequestBody AccountVisit accountId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        artworkLikeService.doLike(user.getUser(),artwork_id);
        return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
    }
    @DeleteMapping("/{artwork_id}")
    public ResponseEntity<Success> unLike(@PathVariable Long artwork_id,
                                          //@RequestBody AccountVisit accountId,
                                          @AuthenticationPrincipal UserDetailsImpl user) {
        artworkLikeService.unLike(user.getUser(),artwork_id);
        return new ResponseEntity<>(new Success("작품 좋아요 완료",""), HttpStatus.OK);
    }


}
