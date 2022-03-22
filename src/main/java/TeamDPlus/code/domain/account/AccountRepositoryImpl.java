package TeamDPlus.code.domain.account;

import TeamDPlus.code.domain.account.rank.QRank;
import TeamDPlus.code.domain.artwork.QArtWorks;
import TeamDPlus.code.domain.artwork.image.QArtWorkImage;
import TeamDPlus.code.dto.response.AccountResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.account.rank.QRank.rank;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;

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






