<<<<<<< HEAD
//package TeamDPlus.code.domain.artwork;
//
//import TeamDPlus.code.domain.account.Account;
//import TeamDPlus.code.domain.account.AccountRepository;
//import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMark;
//import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
//import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
//import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
//import TeamDPlus.code.dto.response.ArtWorkResponseDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static TeamDPlus.code.domain.account.QAccount.account;
//import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
//import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
//import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest
//@Transactional
//@Slf4j
//class ArtWorkRepositoryTest {
//
//    @Autowired
//    JPAQueryFactory queryFactory;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    ArtWorkRepository artWorkRepository;
//
//    @Autowired
//    ArtWorkImageRepository artWorkImageRepository;
//
//    @Autowired
//    ArtWorkBookMarkRepository artWorkBookMarkRepository;
//
//    @Autowired
//    EntityManager em;
//
//
//    @Test
//    public void artwork_feed_query_test() throws Exception {
//        //given account,artwork,
//        Account testAccount = Account.builder()
//                .email("test")
//                .nickname("test")
//                .titleContent("test")
//                .subContent("test")
//                .career(1)
//                .tendency("무슨무슨형")
//                .build();
//        Account saveAccount = accountRepository.save(testAccount);
//        ArtWorks testArtWorks = ArtWorks.builder()
//                .scope(true)
//                .title("test")
//                .content("test")
//                .view(1L)
//                .category("test")
//                .account(saveAccount)
//                .build();
//        testArtWorks.updateArtWorkIsMaster();
//        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
//        em.flush();
//        em.clear();
//        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
//                .artWorks(saveArtWork)
//                .artworkImg("test.img")
//                .build();
//        artWorkImageRepository.save(testArtWorkImage);
//        em.flush();
//        em.clear();
//        //log.info(String.valueOf(saveCareer.getArtWorks().getArtWorkImage().size()));
//
//        //when
//        List<ArtWorkResponseDto.ArtWorkFeed> fetch = queryFactory
//                .select(Projections.constructor(
//                        ArtWorkResponseDto.ArtWorkFeed.class,
//                        artWorks.id,
//                        artWorks.scope,
//                        artWorkImage.artworkImg,
//                        artWorks.view,
//                        artWorks.isMaster,
//                        artWorks.created,
//                        artWorks.modified
//                ))
//                .from(artWorks)
//                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
//                .where(artWorks.isMaster.isTrue().and(artWorks.account.id.eq(saveAccount.getId())))
//                .fetch();
//        //then
//        assertThat(fetch.get(0).getArtwork_id()).isEqualTo(testArtWorks.getId());
//        assertThat(fetch.get(0).getImg()).isEqualTo(testArtWorkImage.getArtworkImg());
//    }
//
//
//    @Test
//    public void artwork_bookmark_test() throws Exception {
//        //give
//        Account account1 = testAccountSet();
//        ArtWorks artWorks1 = testArtWorksSet(account1);
//        ArtWorks artWorks2 = testArtWorksSet(account1);
//        ArtWorkImage artWorkImage1 = testArtWorkImageSet(artWorks1,"test1.img",true);
//        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks2,"test2.img",true);
//
//        ArtWorkBookMark testArtWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks1).account(account1).build();
//        artWorkBookMarkRepository.save(testArtWorkBookMark);
//        em.flush();
//        em.clear();
//        ArtWorkBookMark testArtWorkBookMark1 = ArtWorkBookMark.builder().artWorks(artWorks2).account(account1).build();
//        artWorkBookMarkRepository.save(testArtWorkBookMark1);
//        em.flush();
//        em.clear();
//        //when
//        List<ArtWorkResponseDto.ArtWorkBookMark> bookMarkList = queryFactory
//                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkBookMark.class,
//                        artWorks.id,
//                        artWorks.account.nickname,
//                        artWorkImage.artworkImg,
//                        artWorks.view))
//                .from(artWorks)
//                .join(artWorkBookMark).on(artWorkBookMark.artWorks.eq(artWorks))
//                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
//                .where(artWorkBookMark.account.id.eq(account1.getId()).and(artWorks.scope.isTrue()))
//                .fetch();
//        //then
//
//        assertThat(bookMarkList.get(0).getImg()).isEqualTo(artWorkImage1.getArtworkImg());
//        assertThat(bookMarkList.get(0).getAccount_nickname()).isEqualTo(account1.getNickname());
//        assertThat(bookMarkList.get(1).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
//        assertThat(bookMarkList.get(1).getAccount_nickname()).isEqualTo(account1.getNickname());
//    }
//
//    @Test
//    public void 작품_전체_불러오기() throws Exception {
//        //given
//        Account account1 = testAccountSet();
//        ArtWorks artWorks1 = testArtWorksSet(account1);
//        ArtWorks artWorks2 = testArtWorksSet(account1);
//        ArtWorkImage artWorkImage1 = testArtWorkImageSet(artWorks1,"test1.img",false);
//        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks1,"test2.img",true);
//        ArtWorkImage artWorkImage3 = testArtWorkImageSet(artWorks2, "test3.img", true);
//        ArtWorkImage artWorkImage4 = testArtWorkImageSet(artWorks2, "test4.img", false);
//        Pageable paging = PageRequest.of(0,4);
//        //when
//        List<ArtWorkResponseDto.ArtworkMain> result = queryFactory
//                .select(Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
//                        artWorks.id,
//                        account.id,
//                        account.nickname,
//                        account.profileImg,
//                        artWorkImage.artworkImg,
//                        artWorks.view,
//                        artWorks.category,
//                        artWorks.created
//                ))
//                .from(artWorks)
//                .join(account).on(account.id.eq(artWorks.account.id))
//                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks).and(artWorkImage.thumbnail.isTrue()))
//                .offset(paging.getOffset())
//                .limit(paging.getPageSize())
//                .where(artWorks.id.lt(10))
//                .fetch();
//        //then
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
//        assertThat(result.get(0).getAccount_id()).isEqualTo(account1.getId());
//        assertThat(result.get(1).getImg()).isEqualTo(artWorkImage3.getArtworkImg());
//        assertThat(result.get(1).getAccount_id()).isEqualTo(account1.getId());
//
//
//    }
//
//
//    private Account testAccountSet() {
//        Account testAccount = Account.builder()
//                .email("test")
//                .nickname("test")
//                .titleContent("test")
//                .subContent("test")
//                .career(1)
//                .tendency("무슨무슨형")
//                .build();
//        Account save = accountRepository.save(testAccount);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private ArtWorks testArtWorksSet(Account account) {
//        ArtWorks testArtWorks = ArtWorks.builder()
//                .scope(true)
//                .title("test")
//                .content("test")
//                .view(1L)
//                .category("test")
//                .account(account)
//                .build();
//        testArtWorks.updateArtWorkIsMaster();
//        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
//        em.flush();
//        em.clear();
//        return saveArtWork;
//    }
//
//    private ArtWorkImage testArtWorkImageSet(ArtWorks artWorks,String test,boolean thumb) {
//        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
//                .artWorks(artWorks)
//                .artworkImg(test)
//                .thumbnail(thumb)
//                .build();
//        ArtWorkImage save = artWorkImageRepository.save(testArtWorkImage);
//
//        em.flush();
//        em.clear();
//        return save;
//    }
//}
=======
package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.QAccount;
import TeamDPlus.code.domain.account.rank.Rank;
import TeamDPlus.code.domain.account.rank.RankRepository;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMark;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
import TeamDPlus.code.domain.artwork.comment.ArtWorkComment;
import TeamDPlus.code.domain.artwork.comment.ArtWorkCommentRepository;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikes;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikesRepository;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
import static TeamDPlus.code.domain.artwork.comment.QArtWorkComment.artWorkComment;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
import static TeamDPlus.code.domain.artwork.like.QArtWorkLikes.artWorkLikes;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Slf4j
class ArtWorkRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ArtWorkRepository artWorkRepository;

    @Autowired
    ArtWorkImageRepository artWorkImageRepository;

    @Autowired
    ArtWorkBookMarkRepository artWorkBookMarkRepository;

    @Autowired
    ArtWorkCommentRepository artWorkCommentRepository;

    @Autowired
    ArtWorkLikesRepository artWorkLikesRepository;

    @Autowired
    EntityManager em;

    @Autowired
    RankRepository rankRepository;
    @Test
    public void artwork_feed_query_test() throws Exception {
        //given account,artwork,
        Rank rank = Rank.builder().rankScore(0L).build();
        Rank saveRank = rankRepository.save(rank);
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .titleContent("test")
                .subContent("test")
                .career(1)
                .tendency("무슨무슨형")
                .rank(saveRank)
                .build();
        Account saveAccount = accountRepository.save(testAccount);
        ArtWorks testArtWorks = ArtWorks.builder()
                .scope(true)
                .title("test")
                .content("test")
                .view(1L)
                .category("test")
                .account(saveAccount)
                .build();
        testArtWorks.updateArtWorkIsMaster();
        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
        em.flush();
        em.clear();
        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
                .artWorks(saveArtWork)
                .artworkImg("test.img")
                .build();
        artWorkImageRepository.save(testArtWorkImage);
        em.flush();
        em.clear();
        //log.info(String.valueOf(saveCareer.getArtWorks().getArtWorkImage().size()));

        //when
        List<ArtWorkResponseDto.ArtWorkFeed> fetch = queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorkImage.artworkImg,
                        artWorks.isMaster

                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .where(artWorks.isMaster.isTrue().and(artWorks.account.id.eq(saveAccount.getId())))
                .fetch();
        //then
        assertThat(fetch.get(0).getArtwork_id()).isEqualTo(testArtWorks.getId());
        assertThat(fetch.get(0).getImg()).isEqualTo(testArtWorkImage.getArtworkImg());
    }


    @Test
    public void artwork_bookmark_test() throws Exception {
        //give
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);
        ArtWorks artWorks2 = testArtWorksSet(account1);
        ArtWorkImage artWorkImage1 = testArtWorkImageSet(artWorks1,"test1.img",true);
        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks2,"test2.img",true);

        ArtWorkBookMark testArtWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks1).account(account1).build();
        artWorkBookMarkRepository.save(testArtWorkBookMark);
        em.flush();
        em.clear();
        ArtWorkBookMark testArtWorkBookMark1 = ArtWorkBookMark.builder().artWorks(artWorks2).account(account1).build();
        artWorkBookMarkRepository.save(testArtWorkBookMark1);
        em.flush();
        em.clear();
        //when
        List<ArtWorkResponseDto.ArtWorkBookMark> bookMarkList = queryFactory
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
                .where(artWorkBookMark.account.id.eq(account1.getId()).and(artWorks.scope.isTrue()))
                .groupBy(artWorks.id)
                .fetch();
        //then

        assertThat(bookMarkList.get(0).getImg()).isEqualTo(artWorkImage1.getArtworkImg());
        assertThat(bookMarkList.get(0).getAccount_nickname()).isEqualTo(account1.getNickname());
        assertThat(bookMarkList.get(1).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
        assertThat(bookMarkList.get(1).getAccount_nickname()).isEqualTo(account1.getNickname());
    }

    @Test
    public void 작품_전체_불러오기() throws Exception {
        //given
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);
        ArtWorks artWorks2 = testArtWorksSet(account1);
        testArtWorkImageSet(artWorks1,"test1.img",false);
        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks1,"test2.img",true);
        ArtWorkImage artWorkImage3 = testArtWorkImageSet(artWorks2, "test3.img", true);
        testArtWorkImageSet(artWorks2, "test4.img", false);
        testLikeSet(artWorks1,account1);
        testLikeSet(artWorks1,account1);
        testLikeSet(artWorks1,account1);
        Pageable paging = PageRequest.of(0,4);
        //when
        List<ArtWorkResponseDto.ArtworkMain> result = queryFactory
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
                .where(artWorks.id.lt(10))
                .groupBy(artWorks.id)
                .fetch();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getLike_count()).isEqualTo(3);
        assertThat(result.get(1).getLike_count()).isEqualTo(0);
        assertThat(result.get(0).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
        assertThat(result.get(0).getAccount_id()).isEqualTo(account1.getId());
        assertThat(result.get(1).getImg()).isEqualTo(artWorkImage3.getArtworkImg());
        assertThat(result.get(1).getAccount_id()).isEqualTo(account1.getId());
    }

    @Test
    public void 게시글_sub_상세_() throws Exception {
        //given
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);

        testCommentSet(artWorks1, account1);
        testCommentSet(artWorks1, account1);
//        testCommentSet(artWorks1, account1);
//        testCommentSet(artWorks1, account1);
        testLikeSet(artWorks1, account1);
        testLikeSet(artWorks1, account1);
        testLikeSet(artWorks1, account1);
//        testLikeSet(artWorks1, account1);
        //when
        ArtWorkResponseDto.ArtWorkSubDetail fetch = queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkSubDetail.class,
                        artWorks.id,
                        account.id,
                        artWorks.title,
                        artWorks.content,
                        artWorks.view,
                        artWorkLikes.id.count(),
                        artWorks.category,
                        artWorks.created,
                        artWorks.modified,
                        artWorks.specialty,
                        account.nickname,
                        account.profileImg
                ))
                .from(artWorks)
                .groupBy(artWorks.id)
                .innerJoin(artWorks.account, account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .where(artWorks.id.eq(artWorks1.getId()))
                .fetchOne();
        //then
        assert fetch != null;

        assertThat(fetch.getLike_count()).isEqualTo(3);

    }

    @Test
    @Commit
    public void 가장_좋아요가_많고_조회수가_많은_작품() throws Exception {
        //given
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);
        ArtWorks artWorks2 = testArtWorksSet(account1);
        ArtWorks artWorks3 = testArtWorksSet(account1);
        ArtWorks artWorks4 = testArtWorksSet(account1);
        testLikeSet(artWorks1,account1); //2
        testLikeSet(artWorks2,account1); //2
        testLikeSet(artWorks2,account1); //2
        testLikeSet(artWorks2,account1);
        testLikeSet(artWorks2,account1); // 0
        testLikeSet(artWorks3,account1);
        testLikeSet(artWorks3,account1);
        testLikeSet(artWorks4,account1); //1
        testLikeSet(artWorks4,account1);
        testLikeSet(artWorks4,account1);
        Pageable pageable = PageRequest.of(0,10);
        //만약 10개 이하라면
        //when
        List<ArtWorkResponseDto.ArtworkMain> result = queryFactory
                .select(
                        Projections.constructor(ArtWorkResponseDto.ArtworkMain.class,
                                artWorks.id,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                artWorkImage.artworkImg,
                                artWorks.view,
                                artWorkLikes.id.count(),
                                artWorks.category,
                                artWorks.created
                        ))
                .from(artWorks)
                .join(artWorks.account, account)
                .leftJoin(artWorkLikes).on(artWorkLikes.artWorks.eq(artWorks))
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(artWorks.id)
                .orderBy(artWorkLikes.count().desc(),artWorks.view.desc())
                .where(isInterest(account1.getInterest()))
                .fetch();
        //then
        assert result != null;
        assertThat(result.get(0).getArtwork_id()).isEqualTo(artWorks2.getId());
        assertThat(result.get(1).getArtwork_id()).isEqualTo(artWorks4.getId());
        assertThat(result.get(2).getArtwork_id()).isEqualTo(artWorks3.getId());
        assertThat(result.get(3).getArtwork_id()).isEqualTo(artWorks1.getId());
    }


    public BooleanExpression isInterest(String interest) {
        return interest != null ? artWorks.category.eq(interest) : null;
    }

    private ArtWorkLikes testLikeSet(ArtWorks artWorks, Account account) {
        ArtWorkLikes likes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
        artWorkLikesRepository.save(likes);
        em.flush();
        em.clear();
        return likes;
    }

    private ArtWorkComment testCommentSet(ArtWorks artWorks , Account account) {
        ArtWorkComment comment = ArtWorkComment.builder().artWorks(artWorks).account(account).content("test").build();
        artWorkCommentRepository.save(comment);
        em.flush();
        em.clear();
        return comment;
    }


    private Account testAccountSet() {
        Rank rank = Rank.builder().rankScore(0L).build();
        Rank saveRank = rankRepository.save(rank);
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .titleContent("test")
                .subContent("test")
                .career(1)
                .tendency("무슨무슨형")
                .interest("test")
                .rank(saveRank)
                .build();
        Account save = accountRepository.save(testAccount);
        em.flush();
        em.clear();
        return save;
    }

    private ArtWorks testArtWorksSet(Account account) {
        ArtWorks testArtWorks = ArtWorks.builder()
                .scope(true)
                .title("test")
                .content("test")
                .view(1L)
                .category("test")
                .account(account)
                .build();
        testArtWorks.updateArtWorkIsMaster();
        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
        em.flush();
        em.clear();
        return saveArtWork;
    }

    private ArtWorkImage testArtWorkImageSet(ArtWorks artWorks,String test,boolean thumb) {
        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
                .artWorks(artWorks)
                .artworkImg(test)
                .thumbnail(thumb)
                .build();
        ArtWorkImage save = artWorkImageRepository.save(testArtWorkImage);

        em.flush();
        em.clear();
        return save;
    }
}

>>>>>>> a7950f574314db1a8f3bbd7cbe098b70a1056832
