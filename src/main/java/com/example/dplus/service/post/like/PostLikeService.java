package com.example.dplus.service.post.like;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.domain.post.PostLikes;
import com.example.dplus.repository.post.like.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    @Transactional
    public void doLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (postLikesRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        PostLikes postLikes = PostLikes.builder().post(post).account(account).build();
        postLikesRepository.save(postLikes);
    }

    @Transactional
    public void unLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (!postLikesRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        postLikesRepository.deleteByPostIdAndAccountId(post.getId(),account.getId());
    }
}
