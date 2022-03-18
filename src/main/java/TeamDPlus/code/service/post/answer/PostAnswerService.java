package TeamDPlus.code.service.post.answer;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.answer.PostAnswer;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostAnswerService {

    private final PostRepository postRepository;
    private final PostAnswerRepository postAnswerRepository;

    @Transactional
    public Long createAnswer(PostRequestDto.PostAnswer dto, Long postId, Account account) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("게시글이 존재하지 않습니다."));
        PostAnswer postAnswer = PostAnswer.builder().post(post).account(account).content(dto.getContent()).build();
        PostAnswer save = postAnswerRepository.save(postAnswer);
        return save.getId();
    }

    @Transactional
    public Long updateAnswer(PostRequestDto.PostAnswer dto, Long answerId, Long accountId) {
        PostAnswer postAnswer = postAnswerRepository.findById(answerId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글이거나, 댓글입니다."));

        if (!postAnswer.getAccount().getId().equals(accountId)) {
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }

        postAnswer.updateComment(dto.getContent());
        return postAnswer.getId();
    }

    @Transactional
    public void deleteAnswer(Long answerId, Long accountId) {
        PostAnswer postAnswer = postAnswerRepository.findById(answerId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글이거나, 댓글입니다."));

        if (!postAnswer.getAccount().getId().equals(accountId)) {
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }

        postAnswerRepository.deleteById(answerId);
    }

}
