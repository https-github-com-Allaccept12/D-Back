package TeamDPlus.code.domain.account;

import TeamDPlus.code.domain.account.rank.QRank;
import TeamDPlus.code.dto.response.AccountResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.account.rank.QRank.rank;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AccountResponseDto.TopArtist> findTopArtist() {
        return queryFactory
                .select(
                        Projections.constructor(AccountResponseDto.TopArtist.class,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                account.job))
                .from(account)
                .innerJoin(account.rank, rank)
                .offset(0)
                .limit(10)
                .orderBy(rank.rankScore.desc())
                .fetch();
    }
}
