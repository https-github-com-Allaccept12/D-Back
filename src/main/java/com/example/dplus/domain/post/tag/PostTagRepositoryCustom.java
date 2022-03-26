package com.example.dplus.domain.post.tag;

import com.example.dplus.dto.common.CommonDto;

import java.util.List;

public interface PostTagRepositoryCustom {

    List<CommonDto.PostTagDto> findPostTagListByPostId(Long postId);
}
