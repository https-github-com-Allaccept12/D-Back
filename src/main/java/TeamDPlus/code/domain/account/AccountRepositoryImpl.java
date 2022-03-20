package TeamDPlus.code.domain.account;

import TeamDPlus.code.dto.response.AccountResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.account.rank.QRank.rank;

@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AccountResponseDto.TopArtist> findTopArtist(Pageable pageable) {
        return queryFactory
                .select(
                        Projections.constructor(AccountResponseDto.TopArtist.class,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                account.job))
                .from(account)
                .innerJoin(account.rank, rank)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(rank.rankScore.desc())
                .fetch();
    }
}
