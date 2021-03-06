package com.example.dplus.repository.account.rank;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.account.QRank.rank;

@RequiredArgsConstructor
public class RankRepositoryImpl implements RankRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public void RankInitializationBulk() {
        queryFactory
                .update(rank)
                .set(rank.rankScore, 0L)
                .execute();
    }
}
