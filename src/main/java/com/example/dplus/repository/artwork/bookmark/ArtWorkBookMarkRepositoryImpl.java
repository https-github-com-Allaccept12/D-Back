package com.example.dplus.repository.artwork.bookmark;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.artwork.QArtWorkBookMark.artWorkBookMark;



@RequiredArgsConstructor
public class ArtWorkBookMarkRepositoryImpl implements ArtWorkBookMarkRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public boolean existByAccountIdAndArtWorkId(Long accountId, Long artWorkId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(artWorkBookMark)
                .where(artWorkBookMark.account.id.eq(accountId).and(artWorkBookMark.artWorks.id.eq(artWorkId)))
                .fetchFirst(); // limit 1
        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }
}
