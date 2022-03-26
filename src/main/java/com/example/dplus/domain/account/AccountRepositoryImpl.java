package com.example.dplus.domain.account;

import com.example.dplus.dto.response.AccountResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.account.rank.QRank.rank;


@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<AccountResponseDto.TopArtist> findTopArtist(Pageable pageable,String interest) {
        return queryFactory
                .select(
                        Projections.constructor(AccountResponseDto.TopArtist.class,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                account.job,
                                account.bestArtWorkOne,
                                account.bestArtWorkTwo
                                ))
                .from(account)
                .innerJoin(rank).on(rank.eq(account.rank))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(rank.rankScore.desc())
                .fetch();
    }

    @Override
    public void accountCreateCountInitialization() {
        queryFactory
                .update(account)
                .set(account.artWorkCreateCount,0)
                .set(account.postCreateCount, 0)
                .execute();
    }



}






