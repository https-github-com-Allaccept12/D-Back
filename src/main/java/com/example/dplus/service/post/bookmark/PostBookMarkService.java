package com.example.dplus.service.post.bookmark;

import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.domain.post.PostBookMark;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostBookMarkService {

    private final PostBookMarkRepository postBookMarkRepository;
    private final PostRepository postRepository;

    @Transactional
    @CacheEvict(value="myBookmarkPost", key="#account.id")
    public void doBookMark(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (postBookMarkRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        PostBookMark postBookMark = PostBookMark.builder().post(post).account(account).build();
        postBookMarkRepository.save(postBookMark);
    }

    @Transactional
    @CacheEvict(value="myBookmarkPost", key="#account.id")
    public void unBookMark(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (!postBookMarkRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        postBookMarkRepository.deleteByPostIdAndAccountId(post.getId(),account.getId());
    }


}
