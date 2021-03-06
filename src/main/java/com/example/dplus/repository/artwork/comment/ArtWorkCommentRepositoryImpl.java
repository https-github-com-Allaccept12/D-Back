package com.example.dplus.repository.artwork.comment;


import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.artwork.QArtWorkComment.artWorkComment;
import static com.example.dplus.domain.artwork.QArtWorks.artWorks;

@RequiredArgsConstructor
public class ArtWorkCommentRepositoryImpl implements ArtWorkCommentRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkComment> findArtWorkCommentByArtWorksId(Long artWorkId) {
        return queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkComment.class,
                        artWorkComment.account.id,
                        artWorkComment.id,
                        artWorkComment.content,
                        artWorkComment.modified,
                        account.nickname,
                        account.profileImg
                        ))
                .from(artWorkComment)
                .join(artWorkComment.account,account)
                .join(artWorkComment.artWorks,artWorks)
                .where(artWorks.id.eq(artWorkId))
                .fetch();
    }
}
