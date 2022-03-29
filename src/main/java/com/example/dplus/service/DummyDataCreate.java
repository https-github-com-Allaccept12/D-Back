//package com.example.dplus.service;
//
//import com.example.dplus.repository.account.AccountRepository;
//import com.example.dplus.repository.account.follow.FollowRepository;
//import com.example.dplus.repository.account.history.HistoryRepository;
//import com.example.dplus.repository.account.rank.RankRepository;
//import com.example.dplus.repository.artwork.ArtWorkRepository;
//import com.example.dplus.repository.artwork.bookmark.ArtWorkBookMarkRepository;
//import com.example.dplus.repository.artwork.comment.ArtWorkCommentRepository;
//import com.example.dplus.repository.artwork.image.ArtWorkImageRepository;
//import com.example.dplus.repository.artwork.like.ArtWorkLikesRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//
//
//@Component
//@Transactional
//@RequiredArgsConstructor
//public class DummyDataCreate {
//
//    private final AccountRepository accountRepository;
//
//    private final RankRepository rankRepository;
//
//    private final ArtWorkCommentRepository artWorkCommentRepository;
//    private final ArtWorkLikesRepository artWorkLikesRepository;
//
//    private final ArtWorkRepository artWorkRepository;
//
//    private final ArtWorkImageRepository artWorkImageRepository;
//
//    private final ArtWorkBookMarkRepository artWorkBookMarkRepository;
//
//    private final FollowRepository followRepository;
//
//    private final HistoryRepository historyRepository;
//
//
//    @PostConstruct
//    public void testDummyCreate() {
////        for (int i = 0; i < 10000; i++) {
////            Rank rank = Rank.builder().rankScore(0L).build();
////            rankRepository.save(rank);
////            Account account = Account.builder().accountName("dummyUser" + i).nickname("dummy" + i).email("dummy" + i)
////                    .rank(rank).other("dummy").specialty("dummy").titleContent("dummyContent" + i).subContent("dummuContent" + i).interest("testCategory1")
////                    .tendency("dummy").career(1).linkedIn("linked" + i).brunch("brunch").exp(0L).build();
////            Account saveAccount = accountRepository.save(account);
////        }
////        for (long i = 3L; i < 5000; i++) {
////            Account account = accountRepository.findById(i).get();
////            ArtWorks save = artWorkRepository.save(ArtWorks.builder()
////                    .account(account)
////                    .category("testCategory1")
////                    .content("Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()" +
////                            "Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()" +
////                            "Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()")
////                    .scope(true)
////                    .title("dto.getTitle()")
////                    .workStart("dto.getWork_start()")
////                    .workEnd("dto.getWork_end()")
////                    .isMaster(true)
////                    .specialty("skill")
////                    .copyright("판권")
////                    .thumbnail("dummy.jpg")
////                    .view(0L)
////                    .build());
////
////        }
////        for (int i = 0; i < 10; i++) {
////            Account account = accountRepository.findById(5044L).get();
////            artWorkRepository.save(ArtWorks.builder()
////                    .account(account)
////                    .category("testCategory1")
////                    .content("Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()" +
////                            "Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()" +
////                            "Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()Content()")
////                    .scope(true)
////                    .title("dto.getTitle()")
////                    .workStart("dto.getWork_start()")
////                    .workEnd("dto.getWork_end()")
////                    .isMaster(false)
////                    .specialty("skill")
////                    .copyright("판권")
////                    .thumbnail("dummy.jpg")
////                    .view(0L)
////                    .build());
////        }
////        for (long i = 3L; i < 10003; i++) {
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy1.jpg").build());
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy2.jpg").build());
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy3.jpg").build());
////        }
////        for (long i = 3L; i < 100; i++) {
////            Account account = accountRepository.findById(10003L).get();
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            ArtWorkComment artWorkComment = ArtWorkComment.builder().artWorks(artWorks).account(account).content("test"+i).build();
////            artWorkCommentRepository.save(artWorkComment);
////            ArtWorkLikes artWorkLikes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
////            artWorkLikesRepository.save(artWorkLikes);
////            ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
////            artWorkBookMarkRepository.save(artWorkBookMark);
////        }
////        for (long i = 5L; i < 500; i++) {
////            Account account = accountRepository.findById(i).get();
////            Follow follow = Follow.builder().followerId(4L).followingId(i).build();
////            followRepository.save(follow);
////        }
////        for (long i = 5L; i < 500; i++) {
////            Account account = accountRepository.findById(i).get();
////            Follow follow = Follow.builder().followerId(i).followingId(4L).build();
////            followRepository.save(follow);
////        }
//
////        Account account = accountRepository.findById(10003L).get();
////        for (long i = 2L; i < 100; i++) {
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
////            artWorkBookMarkRepository.save(artWorkBookMark);
////        }
//
//    }
//
//    public void testDummyArtWorkCreate() {
//
//    }
//
//
//
////        for (int i = 3000; i < 6000; i++) {
////            Rank rank = Rank.builder().rankScore(0L).build();
////            Rank saveRank = rankRepository.save(rank);
////            Account account1 = Account.builder().accountName("dummyUser"+i).nickname("dummy"+i).email("dummy"+i)
////                    .rank(saveRank).other("dummy").specialty("dummy").titleContent("dummyContent"+i).subContent("dummuContent"+i).interest("testCategory2")
////                    .tendency("dummy").career(1).linkedIn("linked"+i).brunch("brunch").exp(0L).build();
////            Account saveAccount1 = accountRepository.save(account1);
////            entityManager.flush();
////            entityManager.clear();
////        }
////
////
////        for (int i = 6000; i < 10000; i++) {
////            Rank rank = Rank.builder().rankScore(0L).build();
////            Rank saveRank = rankRepository.save(rank);
////            Account account2 = Account.builder().accountName("dummyUser"+i).nickname("dummy"+i).email("dummy"+i)
////                    .rank(saveRank).other("dummy").specialty("dummy").titleContent("dummyContent"+i).subContent("dummuContent"+i).interest("testCategory3")
////                    .tendency("dummy").career(1).linkedIn("linked"+i).brunch("brunch").exp(0L).build();
////            Account saveAccount2 = accountRepository.save(account2);
////            entityManager.flush();
////            entityManager.clear();
////        }
//
//
//
//
//
//
//
//
//}
