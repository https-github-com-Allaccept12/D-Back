package com.example.dplus.repository.artwork.image;

import com.example.dplus.dto.common.CommonDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dplus.domain.artwork.QArtWorkImage.artWorkImage;
import static com.example.dplus.domain.artwork.QArtWorks.artWorks;


@RequiredArgsConstructor
public class ArtWorkImageRepositoryImpl implements ArtworkImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommonDto.ImgUrlDto> findArtWorkImageByTopView(Long accountId) {
        return queryFactory
                .select(Projections.constructor(CommonDto.ImgUrlDto.class,
                        artWorkImage.artworkImg
                        ))
                .from(artWorkImage)
                .leftJoin(artWorkImage.artWorks, artWorks).on(artWorks.scope.isTrue())
                .limit(2)
                .orderBy(artWorks.view.desc())
                .fetch();
    }

}
