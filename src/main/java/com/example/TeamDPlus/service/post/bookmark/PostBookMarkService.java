package com.example.TeamDPlus.service.post.bookmark;

import com.example.TeamDPlus.advice.ApiRequestException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.post.Post;
import com.example.TeamDPlus.domain.post.PostRepository;
import com.example.TeamDPlus.domain.post.bookmark.PostBookMark;
import com.example.TeamDPlus.domain.post.bookmark.PostBookMarkRepository;
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
