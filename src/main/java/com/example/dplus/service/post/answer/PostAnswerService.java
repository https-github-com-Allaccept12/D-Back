package com.example.dplus.service.post.answer;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.domain.post.PostAnswer;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostAnswerService {

    private final PostRepository postRepository;
    private final PostAnswerRepository postAnswerRepository;
    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Long createAnswer(PostRequestDto.PostAnswer dto, Long postId, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        PostAnswer postAnswer = PostAnswer.builder().post(post).account(account).content(dto.getContent()).build();
        PostAnswer save = postAnswerRepository.save(postAnswer);
        account.updateExp(3);
        return save.getId();
    }

    @Transactional
    public Long updateAnswer(PostRequestDto.PostAnswer dto, Long answerId, Long accountId) {
        PostAnswer postAnswer = postAnswerRepository.findById(answerId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));

        if (!postAnswer.getAccount().getId().equals(accountId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

        postAnswer.updateAnswer(dto.getContent());
        return postAnswer.getId();
    }

    @Transactional
    public void deleteAnswer(Long answerId, Long accountId) {
        PostAnswer postAnswer = postAnswerRepository.findById(answerId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));

        if (!postAnswer.getAccount().getId().equals(accountId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        postAnswerLikesRepository.deleteByPostAnswerIdAndAccountId(answerId, accountId);
        postAnswerRepository.deleteById(answerId);
    }

    @Transactional
    public void doIsSelected(Long postAnswerId, Long accountId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));

        if (!postAnswer.getPost().getAccount().getId().equals(accountId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

        if (postAnswer.isSelected()) {
            throw new ErrorCustomException(ErrorCode.ALREADY_SELECTED_ERROR);
        }

        postAnswer.doIsSelected(true);
        postAnswer.getAccount().updateExp(20);

        if (!postAnswer.getPost().getIsSelected()) {
            postAnswer.getPost().doIsSelected(true);
        }
    }
}

