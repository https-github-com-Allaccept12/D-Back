package com.example.dplus.repository.artwork.image;

import com.example.dplus.dto.common.CommonDto;

import java.util.List;

public interface ArtworkImageRepositoryCustom {

    List<CommonDto.ImgUrlDto> findArtWorkImageByTopView(Long accountId);


}
