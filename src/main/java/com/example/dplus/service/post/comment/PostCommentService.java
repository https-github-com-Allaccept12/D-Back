package com.example.dplus.service.post.comment;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.domain.post.PostComment;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.repository.post.comment.PostCommentLikesRepository;
import com.example.dplus.dto.request.PostRequestDto;
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
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
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
                () -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR)
        );
        if (!postComment.getAccount().getId().equals(accountId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return postComment;
    }
}
