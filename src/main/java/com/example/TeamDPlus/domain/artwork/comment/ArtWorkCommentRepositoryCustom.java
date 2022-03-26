package com.example.TeamDPlus.domain.artwork.comment;

import com.example.TeamDPlus.dto.response.ArtWorkResponseDto;

import java.util.List;

public interface ArtWorkCommentRepositoryCustom {

    List<ArtWorkResponseDto.ArtWorkComment> findArtWorkCommentByArtWorksId(Long artWorkId);
}
