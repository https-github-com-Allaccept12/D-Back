package com.example.TeamDPlus.service.post.like;

import com.example.TeamDPlus.advice.ApiRequestException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.post.answer.PostAnswer;
import com.example.TeamDPlus.domain.post.answer.PostAnswerRepository;
import com.example.TeamDPlus.domain.post.like.PostAnswerLikes;
import com.example.TeamDPlus.domain.post.like.PostAnswerLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAnswerLikeService {

    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final PostAnswerRepository postAnswerRepository;

    @Transactional
    public void answerDoLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostAnswerLikes postAnswerLikes = PostAnswerLikes.builder().postAnswer(postAnswer).account(account).build();
        postAnswerLikesRepository.save(postAnswerLikes);

    }

    @Transactional
    public void answerUnLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (!postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postAnswerLikesRepository.deleteByPostAnswerIdAndAccountId(postAnswer.getId(), account.getId());
    }

}
