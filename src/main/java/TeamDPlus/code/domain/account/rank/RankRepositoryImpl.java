package TeamDPlus.code.domain.account.rank;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static TeamDPlus.code.domain.account.rank.QRanks.ranks;

@RequiredArgsConstructor
public class RankRepositoryImpl implements RankRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public void RankInitializationBulk() {
        queryFactory
                .update(ranks)
                .set(ranks.rankScore, 0L)
                .execute();
    }
}
