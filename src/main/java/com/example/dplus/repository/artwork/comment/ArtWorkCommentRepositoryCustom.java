package com.example.dplus.repository.artwork.comment;

import com.example.dplus.dto.response.ArtWorkResponseDto;

import java.util.List;

public interface ArtWorkCommentRepositoryCustom {

    List<ArtWorkResponseDto.ArtWorkComment> findArtWorkCommentByArtWorksId(Long artWorkId);
}
