package com.example.TeamDPlus.domain.post.answer;

import com.example.TeamDPlus.dto.response.AccountResponseDto;
import com.example.TeamDPlus.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostAnswerRepositoryCustom {

    List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId);

    List<AccountResponseDto.MyAnswer> findPostAnswerByAccountId(Long accountId, Pageable pageable);

}
