package com.example.dplus.controller.post;

import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.answer.PostAnswerService;
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
        loginValid(userDetails);
        return new ResponseEntity<>(new Success("질문글 답변 등록 완료",
                postAnswerService.createAnswer(dto, post_id, userDetails.getUser())), HttpStatus.OK);
    }

    @PatchMapping("/answer/{answer_id}")
    public ResponseEntity<Success> updatePostAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long post_answer_id,
                                                     @RequestBody PostRequestDto.PostAnswer dto) {
        loginValid(userDetails);
        return new ResponseEntity<>(new Success("질문글 답변 수정 완료",
                postAnswerService.updateAnswer(dto, post_answer_id, userDetails.getUser().getId())), HttpStatus.OK);
    }

    @DeleteMapping("/answer/{answer_id}")
    public ResponseEntity<Success> deletePostAnswer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long post_answer_id) {
        loginValid(userDetails);
        postAnswerService.deleteAnswer(userDetails.getUser().getId(), post_answer_id);
        return new ResponseEntity<>(new Success("질문글 답변 삭제 완료",""), HttpStatus.OK);
    }

    @PatchMapping("/answer/select/{answer_id}")
    public ResponseEntity<Success> doIsSelected(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long post_answer_id) {
        loginValid(userDetails);
        postAnswerService.doIsSelected(post_answer_id, userDetails.getUser().getId());
        return new ResponseEntity<>(new Success("채택 완료", ""), HttpStatus.OK);
    }

    private void loginValid(UserDetailsImpl user) {
        if (user == null) {
            throw new BadArgumentsValidException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
    }

}
