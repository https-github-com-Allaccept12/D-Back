package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.domain.account.QAccount;
import TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark;
import TeamDPlus.code.domain.artwork.like.QArtWorkLikes;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
import static TeamDPlus.code.domain.artwork.like.QArtWorkLikes.artWorkLikes;

@RequiredArgsConstructor
public class ArtWorkRepositoryImpl implements ArtWorkRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long lastArtWorkId,Pageable paging,
                                                                               Long visitAccountId,Long accountId,boolean isPortfolio) {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.isMaster,
                        artWorks.created,
                        artWorks.modified
                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isPortfolio(isPortfolio)
                                .and(artWorks.account.id.eq(accountId)),
                        isVisitor(visitAccountId, accountId),
                        isLastArtworkId(lastArtWorkId))
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public Page<ArtWorkResponseDto.ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId,Pageable paging,Long accountId) {
        List<ArtWorkResponseDto.ArtWorkBookMark> result = queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkBookMark.class,
                        artWorks.id,
                        artWorks.account.nickname,
                        artWorkImage.artworkImg,
                        artWorks.view))
                .from(artWorks)
                .join(artWorkBookMark).on(artWorkBookMark.artWorks.eq(artWorks))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(artWorkBookMark.account.id.eq(accountId).and(artWorks.scope.eq("public")),
                        isLastArtworkId(lastArtWorkId))
                .fetch();
        return new PageImpl<>(result,paging,result.size());
    }

    @Override
    public List<ArtWorkResponseDto.ArtWorkFeed> findArtWorkByMostViewAndMostLike() {
        return null;
    }

    @Override
    public Page<ArtWorkResponseDto.ArtworkMain> findAllArtWork(Long lastArtworkId, Pageable paging) {

        List<ArtWorkResponseDto.ArtworkMain> result = queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.category,
                        artWorks.created
                ))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isLastArtworkId(lastArtworkId))
                .fetch();
        return new PageImpl<>(result,paging,result.size());
    }

    @Override
    public void updateAllArtWorkIsMasterToFalse(Long accountId) {
        queryFactory
                .update(artWorks)
                .set(artWorks.isMaster, false)
                .where(artWorks.account.id.eq(accountId))
                .execute();
    }

    //in절을 통한 List 벌크
    @Override
    public void updateAllArtWorkIsMasterToTrue(List<Long> accountIdList) {
        queryFactory
                .update(artWorks)
                .set(artWorks.isMaster, true)
                .where(artWorks.account.id.in(accountIdList))
                .execute();
    }

    public BooleanExpression isLastArtworkId(Long lastArtWorkId) {
        return lastArtWorkId != 0 ? artWorks.id.lt(lastArtWorkId) : null;
    }

    //방문자가 로그인을 안했거나, 로그인은 했지만 다른사람 마이페이지에 온사람 이면 scope가 public인 작품만 보여줘라
    public BooleanExpression isVisitor(Long visitAccountId, Long accountId) {
        return visitAccountId.equals(accountId) ? null : artWorks.scope.eq("public");
    }

    public BooleanExpression isPortfolio(boolean isPortfolio) {
        return isPortfolio ? artWorks.isMaster.isTrue() : null;
    }

}
