package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.domain.artwork.comment.QArtWorkComment;
import TeamDPlus.code.domain.artwork.like.QArtWorkLikes;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
import static TeamDPlus.code.domain.artwork.comment.QArtWorkComment.artWorkComment;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
import static TeamDPlus.code.domain.artwork.like.QArtWorkLikes.artWorkLikes;
import static TeamDPlus.code.domain.post.QPost.post;

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
                        artWorkImage.artworkImg,
                        artWorks.isMaster
                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isPortfolio(isPortfolio),
                        artWorks.account.id.eq(visitAccountId),
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

    @Override
    public List<ArtWorkResponseDto.ArtworkMain> findArtWorkByMostViewAndMostLike(String interest, Pageable pageable) {
        return queryFactory
                .select(
                        Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
                                artWorks.id,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                artWorkImage.artworkImg,
                                artWorks.view,
                                artWorkLikes.count(),
                                artWorks.category,
                                artWorks.created
                        ))
                .from(artWorks)
                .join(artWorks.account, account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(artWorks.id)
                .orderBy(artWorkLikes.count().desc(),artWorks.view.desc())
                .where(isInterest(interest))
                .fetch();
//        return new PageImpl<>(result,pageable,result.size());
    }

    @Override
    public List<ArtWorkResponseDto.ArtworkMain> findAllArtWork(Long lastArtworkId, Pageable paging) {

        return queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created
                ))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(isLastArtworkId(lastArtworkId),artWorks.scope.isTrue())
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
                        account.profileImg
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
                        artWorkImage.artworkImg))
                .from(artWorks)
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks)
                        .and(artWorkImage.thumbnail.isTrue()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(artWorks.account.id.eq(accountId).and(artWorks.id.ne(artWorkId)))
                .orderBy(artWorks.created.desc())
                .fetch();


    }

    @Override
    public List<ArtWorkResponseDto.ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorkLikes.count(),
                        artWorks.category,
                        artWorks.created))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastArtworkId(lastArtWorkId),
                        artWorks.title.contains(keyword)
                                .or(artWorks.title.contains(keyword))
                                .or(artWorks.account.nickname.eq(keyword)))
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
    public void updateAllArtWorkIsMasterToTrue(List<Long> accountIdList) {
        queryFactory
                .update(artWorks)
                .set(artWorks.isMaster, true)
                .where(artWorks.account.id.in(accountIdList))
                .execute();
    }

    //account의 interest를 확인하고 메인페이지에 뿌려줄 top10
    public BooleanExpression isInterest(String interest) {
        return interest != null ? artWorks.category.eq(interest) : null;
    }

    public BooleanExpression isLastArtworkId(Long lastArtWorkId) {
        return lastArtWorkId != 0 ? artWorks.id.lt(lastArtWorkId) : null;
    }

    //방문자가 로그인을 안했거나, 로그인은 했지만 다른사람 마이페이지에 온사람 이면 scope가 public인 작품만 보여줘라
    public BooleanExpression isVisitor(Long visitAccountId, Long accountId) {
        return visitAccountId.equals(accountId) ? null : artWorks.scope.isTrue();
    }

    public BooleanExpression isPortfolio(boolean isPortfolio) {
        return isPortfolio ? artWorks.isMaster.isTrue() : null;
    }

}
