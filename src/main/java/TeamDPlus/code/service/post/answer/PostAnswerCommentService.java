package TeamDPlus.code.service.post.answer;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.answer.PostAnswer;
import TeamDPlus.code.domain.post.answer.PostAnswerComment;
import TeamDPlus.code.domain.post.answer.PostAnswerCommentRepository;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAnswerCommentService {

    private final PostAnswerRepository postAnswerRepository;
    private final PostAnswerCommentRepository postAnswerCommentRepository;

    @Transactional
    public Long createAnswerComment(PostRequestDto.PostAnswerComment dto, Long postAnswerId, Account account) {
        PostAnswer postAnswer = postAnswerRepository.findById(postAnswerId).orElseThrow(() -> new ApiRequestException("게시글이 존재하지 않습니다."));
        PostAnswerComment postAnswerComment = PostAnswerComment.builder().postAnswer(postAnswer).account(account).content(dto.getContent()).build();
        PostAnswerComment save = postAnswerCommentRepository.save(postAnswerComment);
        return save.getId();
    }

    @Transactional
    public void updateAnswerComment(Long answerCommentId, PostRequestDto.PostAnswerComment dto, Long accountId) {
        PostAnswerComment postAnswerComment = postAnswerCommentRepository.findById(answerCommentId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글이거나, 댓글입니다."));

        if (!postAnswerComment.getAccount().getId().equals(accountId)) {
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }

        postAnswerComment.updateComment(dto.getContent());
    }

    @Transactional
    public void deleteAnswerComment(Long answerCommentId, Long accountId) {
        PostAnswerComment postAnswerComment = postAnswerCommentRepository.findById(answerCommentId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글이거나, 댓글입니다."));

        if (!postAnswerComment.getAccount().getId().equals(accountId)) {
            throw new IllegalStateException("댓글 작성자가 아닙니다.");
        }

        postAnswerCommentRepository.deleteById(answerCommentId);
    }

}
