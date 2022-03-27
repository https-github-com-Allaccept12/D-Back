package com.example.dplus.repository.artwork.image;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dplus.domain.artwork.QArtWorkImage.artWorkImage;
import static com.example.dplus.domain.artwork.QArtWorks.artWorks;

@RequiredArgsConstructor
public class ArtWorkImageRepositoryImpl implements ArtworkImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<String> findByAllImageUrl(Long artworkId) {
        return queryFactory
                .select(artWorkImage.artworkImg)
                .from(artWorkImage)
                .where(artWorkImage.artWorks.id.eq(artworkId))
                .fetch();
    }
}
