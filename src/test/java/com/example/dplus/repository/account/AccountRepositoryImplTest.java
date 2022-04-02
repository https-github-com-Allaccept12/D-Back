//package com.example.dplus.repository.account;
//
//import com.example.dplus.domain.account.Account;
//import com.example.dplus.domain.account.Follow;
//import com.example.dplus.domain.account.Rank;
//import com.example.dplus.domain.artwork.ArtWorkComment;
//import com.example.dplus.domain.artwork.ArtWorkImage;
//import com.example.dplus.domain.artwork.ArtWorkLikes;
//import com.example.dplus.domain.artwork.ArtWorks;
//import com.example.dplus.repository.account.follow.FollowRepository;
//import com.example.dplus.repository.account.rank.RankRepository;
//import com.example.dplus.repository.artwork.ArtWorkRepository;
//import com.example.dplus.repository.artwork.bookmark.ArtWorkBookMarkRepository;
//import com.example.dplus.repository.artwork.comment.ArtWorkCommentRepository;
//import com.example.dplus.repository.artwork.image.ArtWorkImageRepository;
//import com.example.dplus.repository.artwork.like.ArtWorkLikesRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//@SpringBootTest
//@Transactional
//class AccountRepositoryImplTest {
//
//        @Autowired
//        JPAQueryFactory queryFactory;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    FollowRepository followRepository;
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
//    ArtWorkCommentRepository artWorkCommentRepository;
//
//    @Autowired
//    ArtWorkLikesRepository artWorkLikesRepository;
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    RankRepository rankRepository;
//
//
//
//    @Test
//    public void 탑_아티스트_테스트() throws Exception {
//        //given
//        Account account1 = testAccountSet("1");
//        Account account2 = testAccountSet("2");
//        testArtWorksSet(account1,"act1");
//        testArtWorksSet(account1,"act2");
//        testArtWorksSet(account1,"act3");
//        testArtWorksSet(account1,"act4");
//        testArtWorksSet(account2,"act5");
//        testArtWorksSet(account2,"act6");
//        testArtWorksSet(account2,"act7");
//
////        //when
////        List<AccountResponseDto.TopArtist> result = queryFactory
////                .select(
////                        Projections.constructor(AccountResponseDto.TopArtist.class,
////                                account.id,
////                                account.nickname,
////                                account.profileImg,
////                                account.job,
////                                artWorks.thumbnail,
////                                queryFactory
////                                        .select(artWorks.thumbnail)
////                                        .from(artWorks)
////                                        .where(artWorks.account.id.eq(account.id)
////                                                        .and(artWorks.scope.isTrue()),
////                                                artWorks.id.eq(
////                                                        select(artWorks.id.max())
////                                                        .from(artWorks)
////                                                        .where(artWorks.account.id.eq(account.id))))
////                        ))
////                .from(account)
////                .leftJoin(artWorks).on(artWorks.account.eq(account))
////                .join(rank).on(rank.eq(account.rank))
////                .limit(10)
////                .where(artWorks.scope.isTrue())
////                .orderBy(rank.rankScore.desc())
////                .groupBy(account.id)
////                .fetch();
//        //then
////        for (AccountResponseDto.TopArtist topArtist : result) {
////            System.out.println("topArtist.id = " + topArtist.getAccount_id()
////                    +" topArtist.getSecondArtwork() = " + topArtist.getSecondArtwork()
////                    +" topArtist.getFirstArtwork() = " + topArtist.getFirstArtwork());
////        }
//    }
//
//    private Follow followSet(Long followingId, Long followerId) {
//        Follow follow1 = Follow.builder().followingId(followingId).followerId(followerId).build();
//        Follow save = followRepository.save(follow1);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//
//    private ArtWorkLikes testLikeSet(ArtWorks artWorks, Account account) {
//        ArtWorkLikes likes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
//        artWorkLikesRepository.save(likes);
//        em.flush();
//        em.clear();
//        return likes;
//    }
//
//    private ArtWorkComment testCommentSet(ArtWorks artWorks, Account account) {
//        ArtWorkComment comment = ArtWorkComment.builder().artWorks(artWorks).account(account).content("test").build();
//        artWorkCommentRepository.save(comment);
//        em.flush();
//        em.clear();
//        return comment;
//    }
//
//
//    private Account testAccountSet(String name) {
//        Rank rank = Rank.builder().rankScore(0L).build();
//        Rank saveRank = rankRepository.save(rank);
//        Account testAccount = Account.builder()
//                .email("test")
//                .accountName(name)
//                .nickname("test")
//                .titleContent("test")
//                .subContent("test")
//                .career(1)
//                .tendency("무슨무슨형")
//                .interest("test")
//                .rank(saveRank)
//                .build();
//        Account save = accountRepository.save(testAccount);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private ArtWorks testArtWorksSet(Account account,String thumbnail) {
//        ArtWorks testArtWorks = ArtWorks.builder()
//                .scope(true)
//                .title("test")
//                .content("test")
//                .view(1L)
//                .category("test")
//                .copyright("test")
//                .account(account)
//                .thumbnail(thumbnail)
//                .build();
//        testArtWorks.updateArtWorkIsMaster(true);
//        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
//        em.flush();
//        em.clear();
//        return saveArtWork;
//    }
//
//    private ArtWorkImage testArtWorkImageSet(ArtWorks artWorks, String test, boolean thumb) {
//        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
//                .artWorks(artWorks)
//                .artworkImg(test)
//                .build();
//        ArtWorkImage save = artWorkImageRepository.save(testArtWorkImage);
//
//        em.flush();
//        em.clear();
//        return save;
//    }
//}