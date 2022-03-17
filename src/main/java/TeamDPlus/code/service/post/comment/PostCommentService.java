package TeamDPlus.code.service.post.comment;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;

    public Long createComment(PostRequestDto.PostComment dto, Long postId, Account account){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("게시글이 존재하지 않습니다."));
        PostComment postComment = PostComment.builder().post(post).account(account).content(dto.getContent()).build();
        PostComment save = postCommentRepository.save(postComment);
        return save.getId();
    }

    public void updateComment(Long commentId, PostRequestDto.PostComment dto) {
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글이거나, 댓글입니다."));

        postComment.updateComment(dto);
    }

    public void deleteComment(Long commentId) {
        postCommentRepository.deleteById(commentId);
    }
}
