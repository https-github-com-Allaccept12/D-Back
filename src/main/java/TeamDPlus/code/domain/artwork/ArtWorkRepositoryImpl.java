package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto.ArtworkMain;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.account.follow.QFollow.follow;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
import static TeamDPlus.code.domain.artwork.like.QArtWorkLikes.artWorkLikes;

@RequiredArgsConstructor
public class ArtWorkRepositoryImpl implements ArtWorkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long lastArtWorkId, Pageable paging,
                                                                               Long visitAccountId, Long accountId, boolean isPortfolio) {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorks.thumbnail,
                        artWorks.isMaster
                ))
                .from(artWorks)
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isPortfolio(isPortfolio),
                        artWorks.account.id.eq(visitAccountId),
                        artWorks.scope.isTrue(),
                        isVisitor(visitAccountId, accountId),
                        isLastArtworkId(lastArtWorkId))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<ArtWorkResponseDto.ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId, Pageable paging, Long accountId) {
        return queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkBookMark.class,
                        artWorks.id,
                        artWorks.account.nickname,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorkLikes.count()
                ))
                .from(artWorks)
                .join(artWorkBookMark).on(artWorkBookMark.artWorks.eq(artWorks))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(artWorkBookMark.account.id.eq(accountId).and(artWorks.scope.isTrue()),
                        isLastArtworkId(lastArtWorkId))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    //개선 필요
    @Override
    public List<ArtworkMain> findArtWorkByMostViewAndMostLike(String interest, Pageable pageable) {

        List<ArtworkMain> fetch = queryFactory
                .select(
                        Projections.constructor(ArtworkMain.class,
                                artWorks.id,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                artWorks.thumbnail,
                                artWorks.view,
                                artWorkLikes.count(),
                                artWorks.category,
                                artWorks.created))
                .from(artWorks)
                .join(artWorks.account, account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isInterest(interest))
                .groupBy(artWorks.id)
                .orderBy(artWorkLikes.count().desc(), artWorks.view.desc())
                .fetch();

        if (fetch.size() < 10) {
            return queryFactory
                    .select(
                            Projections.constructor(ArtworkMain.class,
                                    artWorks.id,
                                    account.id,
                                    account.nickname,
                                    account.profileImg,
                                    artWorks.thumbnail,
                                    artWorks.view,
                                    artWorkLikes.count(),
                                    artWorks.category,
                                    artWorks.created
                            ))
                    .from(artWorks)
                    .join(artWorks.account, account)
                    .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .groupBy(artWorks.id)
                    .orderBy(artWorkLikes.count().desc(), artWorks.view.desc())
                    .fetch();
        }
        return fetch;

    }

    @Override
    public List<ArtworkMain> findAllArtWork(Long lastArtworkId, String category, Pageable paging,int sortSign) {
        return queryFactory
                .select(Projections.constructor(ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorks.thumbnail,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created
                ))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isLastArtworkId(lastArtworkId),
                        isCategory(category),
                        artWorks.scope.isTrue())
                .groupBy(artWorks.id)
                .orderBy(isArtWorkSort(sortSign))
                .fetch();
    }



    @Override
    public List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId, Pageable paging) {
         //accountId가 팔로우한 사람 아이디들이 나옴.
        List<Long> followingId = queryFactory
                .select(follow.followingId)
                .from(follow)
                .join(account).on(account.id.eq(follow.followingId))
                .where(follow.followerId.eq(accountId))
                .fetch();
        //현재 카테고리를 확인하고, in절을 이용해 팔로우한 아이디들의 artwork만 뽑아내기
        return queryFactory
                .select(Projections.constructor(ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorks.thumbnail,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created
                ))
                .from(artWorks)
                .join(artWorks.account,account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isLastArtworkId(lastArtWorkId),
                        isCategory(category),
                        artWorks.scope.isTrue(),
                        artWorks.account.id.in(followingId))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public ArtWorkResponseDto.ArtWorkSubDetail findByArtWorkSubDetail( Long artworkId) {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkSubDetail.class,
                        artWorks.id,
                        account.id,
                        artWorks.title,
                        artWorks.content,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created,
                        artWorks.modified,
                        artWorks.specialty,
                        account.nickname,
                        account.profileImg,
                        artWorks.copyright
                ))
                .from(artWorks)
                .innerJoin(artWorks.account, account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .where(artWorks.id.eq(artworkId))
                .groupBy(artWorks.id)
                .fetchOne();
    }



    @Override
    public List<ArtWorkResponseDto.ArtWorkSimilarWork> findSimilarArtWork(Long accountId,Long artWorkId, Pageable pageable) {
        return  queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkSimilarWork.class,
                        artWorks.id,
                        artWorks.title,
                        artWorks.thumbnail))
                .from(artWorks)
                .join(artWorks.account, account)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(artWorks.account.id.eq(accountId).and(artWorks.id.ne(artWorkId)))
                .orderBy(artWorks.created.desc())
                .fetch();


    }

    @Override
    public List<ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorks.thumbnail,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastArtworkId(lastArtWorkId),
                        artWorks.title.contains(keyword),
                        artWorks.content.contains(keyword),
                        artWorks.account.nickname.contains(keyword))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }



    @Override
    public void updateArtWorkIdMasterToFalse(Long artWorkId) {
        queryFactory
                .update(artWorks)
                .set(artWorks.isMaster, false)
                .where(artWorks.id.eq(artWorkId))
                .execute();
    }

    //in절을 통한 List 벌크
    @Override
    public void updateAllArtWorkIsMasterToTrue(List<Long> artworkIdList) {
        queryFactory
                .update(artWorks)
                .set(artWorks.isMaster, true)
                .set(artWorks.scope, true)
                .where(artWorks.id.in(artworkIdList))
                .execute();
    }

    private OrderSpecifier<?> isArtWorkSort(int sortSign) {
        return sortSign == 1 ? artWorks.created.desc() : artWorkLikes.count().desc();
    }

    private BooleanExpression isCategory(String category) {
        return category.isEmpty() ? null : artWorks.category.eq(category);
    }

    //account의 interest를 확인하고 메인페이지에 뿌려줄 top10
    private BooleanExpression isInterest(String interest) {
        return interest != null ? artWorks.category.eq(interest) : null;
    }

    private BooleanExpression isLastArtworkId(Long lastArtWorkId) {
        return lastArtWorkId != 0 ? artWorks.id.lt(lastArtWorkId) : null;
    }

    //방문자가 로그인을 안했거나, 로그인은 했지만 다른사람 마이페이지에 온사람 이면 scope가 public인 작품만 보여줘라
    private BooleanExpression isVisitor(Long visitAccountId, Long accountId) {
        return visitAccountId.equals(accountId) ? null : artWorks.scope.isTrue();
    }

    private BooleanExpression isPortfolio(boolean isPortfolio) {
        return isPortfolio ? artWorks.isMaster.isTrue() : null;
    }

}
