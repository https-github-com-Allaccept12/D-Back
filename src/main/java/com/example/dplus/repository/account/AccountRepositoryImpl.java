package com.example.dplus.repository.account;

import com.example.dplus.domain.account.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.account.QRank.rank;

@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    @Override
    public List<Account> findTopArtist(String interest) {
        return queryFactory
                .selectFrom(account)
                .join(rank).on(rank.eq(account.rank))
                .limit(10)
                .orderBy(rank.rankScore.desc())
                .fetch();
    }

    @Override
    public void accountCreateCountInitialization() {
        queryFactory
                .update(account)
                .set(account.artWorkCreateCount, 0)
                .set(account.postCreateCount, 0)
                .execute();
    }

}






