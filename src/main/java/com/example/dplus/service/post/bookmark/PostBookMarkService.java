package com.example.dplus.service.post.bookmark;

import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.Post;
import com.example.dplus.domain.post.PostRepository;
import com.example.dplus.domain.post.bookmark.PostBookMark;
import com.example.dplus.domain.post.bookmark.PostBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostBookMarkService {

    private final PostBookMarkRepository postBookMarkRepository;
    private final PostRepository postRepository;

    @Transactional
    public void doBookMark(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (postBookMarkRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        PostBookMark postBookMark = PostBookMark.builder().post(post).account(account).build();
        postBookMarkRepository.save(postBookMark);
    }

    @Transactional
    public void unBookMark(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
        if (!postBookMarkRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        postBookMarkRepository.deleteByPostIdAndAccountId(post.getId(),account.getId());
    }

}