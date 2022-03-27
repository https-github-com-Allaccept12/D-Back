package com.example.dplus.service.post.comment.like;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.PostComment;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.domain.post.PostCommentLikes;
import com.example.dplus.repository.post.comment.PostCommentLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommentLikeService {
    private final PostCommentLikesRepository postCommentLikesRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public void doLike(Account account, Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (postCommentLikesRepository.existByAccountIdAndPostCommentId(account.getId(), postCommentId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostCommentLikes postCommentLikes = PostCommentLikes.builder().postComment(postComment).account(account).build();
        postCommentLikesRepository.save(postCommentLikes);
    }

    @Transactional
    public void unLike(Account account, Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (!postCommentLikesRepository.existByAccountIdAndPostCommentId(account.getId(), postCommentId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postCommentLikesRepository.deleteByPostCommentIdAndAccountId(postComment.getId(),account.getId());
    }
}
