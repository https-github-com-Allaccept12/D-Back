package TeamDPlus.code.controller.post;

import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
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
    public ResponseEntity<Success> createPostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long post_id,
                                                     @RequestBody PostRequestDto.PostAnswer dto) {
        if (user != null) {
            return new ResponseEntity<>(new Success("질문글 답변 등록 완료",
                    postAnswerService.createAnswer(dto, post_id, user.getUser())), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/answer/{answer_id}")
    public ResponseEntity<Success> updatePostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long answer_id,
                                                     @RequestBody PostRequestDto.PostAnswer dto) {
        if (user != null) {
            return new ResponseEntity<>(new Success("질문글 답변 수정 완료",
                    postAnswerService.updateAnswer(dto, answer_id, user.getUser().getId())), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/answer/{answer_id}")
    public ResponseEntity<Success> deletePostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                     @PathVariable Long answer_id) {
        if (user != null) {
            postAnswerService.deleteAnswer(answer_id, user.getUser().getId());
            return new ResponseEntity<>(new Success("질문글 답변 삭제 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/answer/select/{answer_id}")
    public ResponseEntity<Success> doIsSelected(@AuthenticationPrincipal UserDetailsImpl user,
                                                @PathVariable Long answer_id) {
        if (user != null) {
            postAnswerService.doIsSelected(answer_id, user.getUser().getId());
            return new ResponseEntity<>(new Success("채택 완료", ""), HttpStatus.OK);
        }
        throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

}
