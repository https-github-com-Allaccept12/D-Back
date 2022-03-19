package TeamDPlus.code.service.post.comment;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikesRepository;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final PostCommentLikesRepository postCommentLikesRepository;

    public Long createComment(Account account, Long postId, PostRequestDto.PostComment dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("게시글이 존재하지 않습니다."));
        PostComment postComment = PostComment.builder().post(post).account(account).content(dto.getContent()).build();
        PostComment save = postCommentRepository.save(postComment);
        return save.getId();
    }

    public Long updateComment(Long accountId, Long commentId, PostRequestDto.PostComment dto) {
        PostComment postComment = commentValidation(accountId, commentId);
        postComment.updateComment(dto);
        return postComment.getId();
    }

    public void deleteComment(Long accountId, Long postCommentId) {
        PostComment postComment = commentValidation(accountId, postCommentId);
        postCommentLikesRepository.deleteAllByPostCommentId(postCommentId);
        postCommentRepository.deleteById(postCommentId);
    }

    // 코멘트 수정삭제 권한 확인
    public PostComment commentValidation(Long accountId, Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글이거나, 댓글입니다.")
        );
        if (!postComment.getAccount().getId().equals(accountId)) {
            throw new ApiRequestException("댓글 작성자가 아닙니다");
        }
        return postComment;
    }
}
