package com.example.dplus.repository.artwork.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.artwork.QArtWorkLikes.artWorkLikes;


@RequiredArgsConstructor
public class ArtWorkLikesRepositoryImpl implements ArtWorkLikesRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(artWorkLikes)
                .where(artWorkLikes.account.id.eq(accountId).and(artWorkLikes.artWorks.id.eq(artWorkId)))
                .fetchFirst(); // limit 1
        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }
}
