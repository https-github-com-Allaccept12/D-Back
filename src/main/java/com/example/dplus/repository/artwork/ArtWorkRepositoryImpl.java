package com.example.dplus.repository.artwork;

import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtWorkBookMark;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtWorkFeed;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtworkMain;
import com.example.dplus.dto.response.ArtWorkResponseDto.MyArtWork;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.account.QFollow.follow;
import static com.example.dplus.domain.artwork.QArtWorkBookMark.artWorkBookMark;
import static com.example.dplus.domain.artwork.QArtWorkLikes.artWorkLikes;
import static com.example.dplus.domain.artwork.QArtWorks.artWorks;

@RequiredArgsConstructor
public class ArtWorkRepositoryImpl implements ArtWorkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkFeed> findByMasterArtWorkImageAndAccountId(Long visitAccountId) {
        return queryFactory
                .select(Projections.constructor(ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.thumbnail,
                        artWorks.isMaster
                ))
                .from(artWorks)
                .limit(4)
                .where(artWorks.account.id.eq(visitAccountId)
                        .and(artWorks.isMaster.isTrue()))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<MyArtWork> findByArtWork(Long lastArtWorkId,Long visitAccountId, Long accountId) {
//        List<Long> cover = queryFactory
//                .select(artWorks.id)
//                .from(artWorks)
//                .limit(10)
//                .where(
//                        )
//                )
//                .fetch();

        return queryFactory
                .select(Projections.constructor(MyArtWork.class,
                        artWorks.id,
                        artWorks.thumbnail,
                        artWorks.scope,
                        artWorks.isMaster,
                        artWorks.category))
                .from(artWorks)
                .limit(10)
                .innerJoin(artWorks.account,account)
                .where(isVisitor(visitAccountId, accountId),
                        isLastArtworkId(lastArtWorkId),
                        account.id.eq(visitAccountId))
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId, Long accountId) {
        return queryFactory
                .select(Projections.constructor(ArtWorkBookMark.class,
                        artWorks.id,
                        artWorks.account.nickname,
                        artWorks.thumbnail,
                        artWorks.view,
                        artWorkLikes.count()
                ))
                .from(artWorks)
                .join(artWorkBookMark).on(artWorkBookMark.artWorks.eq(artWorks))
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .limit(10)
                .where(artWorkBookMark.account.id.eq(accountId)
                                .and(artWorks.scope.isTrue()),
                        isLastArtworkId(lastArtWorkId))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    //개선 필요
    @Override
    public List<ArtworkMain> findArtWorkByMostViewAndMostLike(String interest) {
        List<Long> Separator = queryFactory
                .select(artWorks.id)
                .from(artWorks)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .limit(10)
                .where(isInterest(interest))
                .fetch();

        //10개
        if (Separator.size() >= 10) {
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
                                    artWorks.created))
                    .from(artWorks)
                    .join(artWorks.account, account)
                    .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                    .limit(10)
                    .where(isInterest(interest),
                            artWorks.scope.isTrue())
                    .groupBy(artWorks.id)
                    .orderBy(artWorkLikes.count().desc())
                    .fetch();
        }
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
                    .limit(10)
                    .groupBy(artWorks.id)
                    .where(artWorks.scope.isTrue())
                    .orderBy(artWorkLikes.count().desc())
                    .fetch();
    }
    @Override
    public List<ArtworkMain> findAllArtWork(Long lastArtworkId, String category) {
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
                .limit(10)
                .where(isLastArtworkId(lastArtworkId),
                        isCategory(category),
                        artWorks.scope.isTrue())
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<ArtworkMain> showArtWorkLikeSort(String category, Pageable pageable) {
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isCategory(category),
                        artWorks.scope.isTrue())
                .groupBy(artWorks.id)
                .orderBy(artWorkLikes.count().desc(),artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId) {

        List<Long> followingId = queryFactory
                .select(follow.followingId)
                .from(follow)
                .join(account).on(account.id.eq(follow.followingId))
                .where(follow.followerId.eq(accountId))
                .fetch();

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
                .limit(10)
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
                        artWorks.thumbnail,
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
    public List<ArtWorkResponseDto.ArtWorkSimilarWork> findSimilarArtWork(Long accountId,Long artWorkId) {
        return  queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkSimilarWork.class,
                        artWorks.id,
                        artWorks.title,
                        artWorks.thumbnail))
                .from(artWorks)
                .join(artWorks.account, account)
                .limit(5)
                .where(artWorks.account.id.eq(accountId)
                                .and(artWorks.id.ne(artWorkId)),
                        artWorks.scope.isTrue())
                .orderBy(artWorks.created.desc())
                .fetch();
    }

    @Override
    public List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId) {

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
                .limit(10)
                .where(isLastArtworkId(lastArtWorkId),
                        (artWorks.title.contains(keyword)
                                .or(artWorks.content.contains(keyword))
                                .or(artWorks.account.nickname.contains(keyword)))
                        .and(artWorks.scope.isTrue()))
                .groupBy(artWorks.id)
                .orderBy(artWorks.created.desc())
                .fetch();
    }



    private BooleanExpression isCategory(String category) {
        return category.isEmpty() ? null : artWorks.category.eq(category);
    }

    //account의 interest를 확인하고 메인페이지에 뿌려줄 top10
    private BooleanExpression isInterest(String interest) {
        return !Objects.equals(interest, "default") ? artWorks.category.eq(interest) : null;
    }

    private BooleanExpression isLastArtworkId(Long lastArtWorkId) {
        return lastArtWorkId != 0 ? artWorks.id.lt(lastArtWorkId) : null;
    }

    //방문자가 로그인을 안했거나, 로그인은 했지만 다른사람 마이페이지에 온사람 이면 scope가 public인 작품만 보여줘라
    private BooleanExpression isVisitor(Long visitAccountId, Long accountId) {
        return visitAccountId.equals(accountId) ? null : artWorks.scope.isTrue();
    }



}
