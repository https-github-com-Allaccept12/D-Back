package TeamDPlus.code.service.post.like;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.answer.PostAnswer;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.domain.post.like.PostAnswerLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostAnswerLikeService {

    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final PostAnswerRepository postAnswerRepository;

    public void doAnswerLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
        if (postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException("이미 좋아요한 게시글 입니다.");
        }

    }

}
