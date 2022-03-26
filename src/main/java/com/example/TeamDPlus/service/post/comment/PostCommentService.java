package com.example.TeamDPlus.service.post.comment;

import com.example.TeamDPlus.advice.ApiRequestException;
import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.post.Post;
import com.example.TeamDPlus.domain.post.PostRepository;
import com.example.TeamDPlus.domain.post.comment.PostComment;
import com.example.TeamDPlus.domain.post.comment.PostCommentRepository;
import com.example.TeamDPlus.domain.post.comment.like.PostCommentLikesRepository;
import com.example.TeamDPlus.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final PostCommentLikesRepository postCommentLikesRepository;

    @Transactional
    public Long createComment(Account account, Long postId, PostRequestDto.PostComment dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        PostComment postComment = PostComment.builder().post(post).account(account).content(dto.getContent()).build();
        PostComment save = postCommentRepository.save(postComment);
        return save.getId();
    }

    @Transactional
    public Long updateComment(Long accountId, Long commentId, PostRequestDto.PostComment dto) {
        PostComment postComment = commentValidation(accountId, commentId);
        postComment.updateComment(dto);
        return postComment.getId();
    }

    @Transactional
    public void deleteComment(Long accountId, Long postCommentId) {
        commentValidation(accountId, postCommentId);
        postCommentLikesRepository.deleteAllByPostCommentId(postCommentId);
        postCommentRepository.deleteById(postCommentId);
    }

    // 코멘트 수정삭제 권한 확인
    public PostComment commentValidation(Long accountId, Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR)
        );
        if (!postComment.getAccount().getId().equals(accountId)) {
            throw new BadArgumentsValidException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return postComment;
    }
}
