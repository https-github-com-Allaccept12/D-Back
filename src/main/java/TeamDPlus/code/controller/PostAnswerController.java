package TeamDPlus.code.controller;

import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.post.PostMainPageService;
import TeamDPlus.code.service.post.answer.PostAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostAnswerController {

    private final PostMainPageService postMainPageService;
    private final PostAnswerService postAnswerService;

    @PostMapping("/api/post")
    public ResponseEntity<Success> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto.PostCreateAndUpdate dto) {
        return new ResponseEntity<>(new Success<>(
                "포스팅 성공", postMainPageService.createPost(userDetails.getUser(), dto)), HttpStatus.OK);
    }

}
