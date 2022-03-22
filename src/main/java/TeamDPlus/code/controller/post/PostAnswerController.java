package TeamDPlus.code.controller.post;

import TeamDPlus.code.dto.Success;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.post.PostMainPageService;
import TeamDPlus.code.service.post.answer.PostAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostAnswerController {

    private final PostAnswerService postAnswerService;

    @PostMapping("/answer/{post_id}")
    public ResponseEntity<Success> createPostAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long post_id,
                                                     @RequestBody PostRequestDto.PostAnswer dto) {
        return new ResponseEntity<>(new Success("질문글 답변 등록 완료",
                postAnswerService.createAnswer(dto, post_id, userDetails.getUser())), HttpStatus.OK);
    }

    @PatchMapping("/answer/{post_answer_id}")
    public ResponseEntity<Success> updatePostAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long post_answer_id,
                                                     @RequestBody PostRequestDto.PostAnswer dto) {
        return new ResponseEntity<>(new Success("질문글 답변 수정 완료",
                postAnswerService.updateAnswer(dto, post_answer_id, userDetails.getUser().getId())), HttpStatus.OK);
    }

    @DeleteMapping("/answer/{post_answer_id}")
    public ResponseEntity<Success> deletePostAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long post_answer_id) {
        postAnswerService.deleteAnswer(userDetails.getUser().getId(), post_answer_id);
        return new ResponseEntity<>(new Success("질문글 답변 삭제 완료",""), HttpStatus.OK);
    }

    @PatchMapping("/answer/select/{post_answer_id}")
    public ResponseEntity<Success> doIsSelected(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long post_answer_id) {
        postAnswerService.doIsSelected(post_answer_id, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success("채택 완료", ""), HttpStatus.OK);
    }

}
