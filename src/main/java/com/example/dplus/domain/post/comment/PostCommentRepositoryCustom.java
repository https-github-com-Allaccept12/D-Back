package com.example.dplus.domain.post.comment;

import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCommentRepositoryCustom {

    List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId);

    List<AccountResponseDto.MyComment> findPostCommentByAccountId(Long accountId, Pageable pageable);
}
