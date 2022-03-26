package com.example.TeamDPlus.domain.post.comment;

import com.example.TeamDPlus.dto.response.AccountResponseDto;
import com.example.TeamDPlus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCommentRepositoryCustom {

    List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId);

    List<AccountResponseDto.MyComment> findPostCommentByAccountId(Long accountId, Pageable pageable);
}
