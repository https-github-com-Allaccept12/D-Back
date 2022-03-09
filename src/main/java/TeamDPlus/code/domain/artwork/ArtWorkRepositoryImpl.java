package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;

@RequiredArgsConstructor
public class ArtWorkRepositoryImpl implements ArtWorkRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long visitAccountId,Long accountId,boolean isPortfolio) {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorks.title,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.created,
                        artWorks.modified
                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .where(isPortfolio(isPortfolio)
                                .and(artWorks.account.id.eq(accountId))
                        , isVisitor(visitAccountId, accountId))
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    //방문자가 로그인을 안했거나, 로그인은 했지만 다른사람 마이페이지에 온사람 이면 scope가 public인 작품만 보여줘라
    public BooleanExpression isVisitor(Long visitAccountId, Long accountId) {
        return visitAccountId.equals(accountId) ? null : artWorks.scope.eq("public");
    }

    public BooleanExpression isPortfolio(boolean isPortfolio) {
        return isPortfolio ? artWorks.isMaster.isTrue() : null;
    }
}
