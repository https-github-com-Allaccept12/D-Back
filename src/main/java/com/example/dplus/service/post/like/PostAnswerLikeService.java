package com.example.dplus.service.post.like;

import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.answer.PostAnswer;
import com.example.dplus.domain.post.answer.PostAnswerRepository;
import com.example.dplus.domain.post.like.PostAnswerLikes;
import com.example.dplus.domain.post.like.PostAnswerLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostAnswerLikeService {

    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final PostAnswerRepository postAnswerRepository;

    public void answerDoLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostAnswerLikes postAnswerLikes = PostAnswerLikes.builder().postAnswer(postAnswer).account(account).build();
        postAnswerLikesRepository.save(postAnswerLikes);

    }

    public void answerUnLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (!postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postAnswerLikesRepository.deleteByPostAnswerIdAndAccountId(postAnswerId, account.getId());
    }

}
