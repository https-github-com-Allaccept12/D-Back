package com.example.dplus.service.post.comment.like;

import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.comment.PostComment;
import com.example.dplus.domain.post.comment.PostCommentRepository;
import com.example.dplus.domain.post.comment.like.PostCommentLikes;
import com.example.dplus.domain.post.comment.like.PostCommentLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentLikeService {
    private final PostCommentLikesRepository postCommentLikesRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public void doLike(Account account, Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postCommentLikesRepository.existByAccountIdAndPostCommentId(account.getId(), postCommentId)) {
            throw new BadArgumentsValidException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostCommentLikes postCommentLikes = PostCommentLikes.builder().postComment(postComment).account(account).build();
        postCommentLikesRepository.save(postCommentLikes);
    }

    @Transactional
    public void unLike(Account account, Long postCommentId) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (!postCommentLikesRepository.existByAccountIdAndPostCommentId(account.getId(), postCommentId)) {
            throw new BadArgumentsValidException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postCommentLikesRepository.deleteByPostCommentIdAndAccountId(postComment.getId(),account.getId());
    }
}