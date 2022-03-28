package com.example.dplus.controller.post;

import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.dto.Success;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.jwt.UserDetailsImpl;
import com.example.dplus.service.post.answer.PostAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostAnswerController {
    private final PostAnswerService postAnswerService;

    @PostMapping("/answer/{post_id}")
    public ResponseEntity<Success> createPostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                    @PathVariable Long post_id,
                                                    @Valid @RequestBody PostRequestDto.PostAnswer dto) {
        if (user != null) {
            return new ResponseEntity<>(new Success("질문글 답변 등록 완료",
                    postAnswerService.createAnswer(dto, post_id, user.getUser().getId())), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/answer/{answer_id}")
    public ResponseEntity<Success> updatePostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                    @PathVariable Long answer_id,
                                                    @Valid @RequestBody PostRequestDto.PostAnswer dto) {
        if (user != null) {
            return new ResponseEntity<>(new Success("질문글 답변 수정 완료",
                    postAnswerService.updateAnswer(dto, answer_id, user.getUser().getId())), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @DeleteMapping("/answer/{answer_id}")
    public ResponseEntity<Success> deletePostAnswer(@AuthenticationPrincipal UserDetailsImpl user,
                                                    @PathVariable Long answer_id) {
        if (user != null) {
            postAnswerService.deleteAnswer(answer_id, user.getUser().getId());
            return new ResponseEntity<>(new Success("질문글 답변 삭제 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

    @PatchMapping("/answer/select/{answer_id}")
    public ResponseEntity<Success> doIsSelected(@AuthenticationPrincipal UserDetailsImpl user,
                                                @PathVariable Long answer_id) {
        if (user != null) {
            postAnswerService.doIsSelected(answer_id, user.getUser().getId());
            return new ResponseEntity<>(new Success("채택 완료", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

}
