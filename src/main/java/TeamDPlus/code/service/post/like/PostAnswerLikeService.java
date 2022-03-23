package TeamDPlus.code.service.post.like;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.post.answer.PostAnswer;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikes;
import TeamDPlus.code.domain.post.like.PostAnswerLikes;
import TeamDPlus.code.domain.post.like.PostAnswerLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAnswerLikeService {

    private final PostAnswerLikesRepository postAnswerLikesRepository;
    private final PostAnswerRepository postAnswerRepository;

    public void answerDoLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
        if (postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException("이미 좋아요한 게시글 입니다.");
        }
        PostAnswerLikes postAnswerLikes = PostAnswerLikes.builder().postAnswer(postAnswer).account(account).build();
        postAnswerLikesRepository.save(postAnswerLikes);

    }

    @Transactional
    public void answerUnLike(Account account, Long postAnswerId) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
        if (!postAnswerLikesRepository.existByAccountIdAndPostAnswerId(account.getId(), postAnswerId)) {
            throw new ApiRequestException("이미 좋아요한 게시글 입니다.");
        }
        postAnswerLikesRepository.deleteByPostAnswerIdAndAccountId(postAnswerId, account.getId());
    }

}
