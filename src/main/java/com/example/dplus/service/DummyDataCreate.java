//package com.example.dplus.service;
//
//import com.example.dplus.domain.account.Account;
//import com.example.dplus.domain.post.Post;
//import com.example.dplus.domain.post.PostBoard;
//import com.example.dplus.repository.account.AccountRepository;
//import com.example.dplus.repository.account.follow.FollowRepository;
//import com.example.dplus.repository.account.history.HistoryRepository;
//import com.example.dplus.repository.account.rank.RankRepository;
//import com.example.dplus.repository.artwork.ArtWorkRepository;
//import com.example.dplus.repository.artwork.bookmark.ArtWorkBookMarkRepository;
//import com.example.dplus.repository.artwork.comment.ArtWorkCommentRepository;
//import com.example.dplus.repository.artwork.image.ArtWorkImageRepository;
//import com.example.dplus.repository.artwork.like.ArtWorkLikesRepository;
//import com.example.dplus.repository.post.PostRepository;
//import com.example.dplus.repository.post.answer.PostAnswerRepository;
//import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
//import com.example.dplus.repository.post.comment.PostCommentRepository;
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
//    private final PostRepository postRepository;
//
//    private final PostBookMarkRepository postBookMarkRepository;
//
//    private final PostAnswerRepository postAnswerRepository;
//
//    private final PostCommentRepository postCommentRepository;
//
//
//    @PostConstruct
//    public void testDummyCreate() {
////        for (long i = 1; i < 10; i++) {
////            Account account = accountRepository.findById(1001L).get();
////            Post post = Post.builder().account(account).board(PostBoard.QNA).category("testcategory1")
////                    .content("내용").title("제목").isSelected(false).build();
////            Post post1 = Post.builder().account(account).board(PostBoard.INFO).category("testcategory2")
////                    .content("내용").title("제목").isSelected(false).build();
////            postRepository.save(post1);
////            postRepository.save(post);
////            Post testpost = postRepository.findById(i).get();
////            PostBookMark postBookMark = PostBookMark.builder().post(testpost).account(account).build();
////            postBookMarkRepository.save(postBookMark);
////
////            PostAnswer postAnswer = PostAnswer.builder().post(testpost).account(account).content("ddasds").isSelected(false).build();
////            postAnswerRepository.save(postAnswer);
//        }
////        for (long i = 501; i < 510; i++) {
////            Account account = accountRepository.findById(1001L).get();
////            Post post = postRepository.findById(i).get();
////            PostComment postComment = PostComment.builder().post(post).account(account).content("ddddascxzczxxczczxzcxxxxxxxxxxxxddddascxzczxxczczxzcxxxxxxxxxxxxddddascxzczxxczczxzcxxxxxxxxxxxxddddascxzczxxczczxzcxxxxxxxxxxxxddddascxzczxxczczxzcxxxxxxxxxxxx").build();
////            postCommentRepository.save(postComment);
////
////        }
//
////        for (int i = 0; i < 1000; i++) {
////            Rank rank = Rank.builder().rankScore(0L).build();
////            rankRepository.save(rank);
////            Account account = Account.builder().accountName("dummyUser" + i).nickname("dummy" + i).email("dummy" + i)
////                    .rank(rank).other("dummy").specialty("dummy").titleContent("dummyContent" + i).subContent("dummuContent" + i).interest("testCategory1")
////                    .tendency("dummy").career(1).linkedIn("linked" + i).brunch("brunch").exp(0L).build();
////            Account saveAccount = accountRepository.save(account);
////        }
////
////        for (long i = 3L; i < 1000; i++) {
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
////
////        for (int i = 0; i < 10; i++) {
////            Account account = accountRepository.findById(1001L).get();
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
////        for (long i = 3L; i < 500; i++) {
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy1.jpg").build());
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy2.jpg").build());
////            artWorkImageRepository.save(ArtWorkImage.builder().artWorks(artWorks).artworkImg("dummy3.jpg").build());
////        }
////        for (long i = 3L; i < 10; i++) {
////            Account account = accountRepository.findById(1001L).get();
////            History history = History.builder().account(account).workStart("start").workEnd("end").achievements("이런 성과있었음").companyDepartment("부서")
////                    .companyName("아무개회사").companyPosition("직책").build();
////            historyRepository.save(history);
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            ArtWorkComment artWorkComment = ArtWorkComment.builder().artWorks(artWorks).account(account).content("test"+i).build();
////            artWorkCommentRepository.save(artWorkComment);
////            ArtWorkLikes artWorkLikes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
////            artWorkLikesRepository.save(artWorkLikes);
////            ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
////            artWorkBookMarkRepository.save(artWorkBookMark);
////            Follow follow = Follow.builder().followerId(1001L).followingId(i).build();
////            followRepository.save(follow);
////            Follow following = Follow.builder().followerId(i).followingId(1001L).build();
////            followRepository.save(following);
////        }
////        for (long i = 5L; i < 500; i++) {
////            Account account = accountRepository.findById(i).get();
////            Follow follow = Follow.builder().followerId(1L).followingId(i).build();
////            followRepository.save(follow);
////        }
////        for (long i = 5L; i < 500; i++) {
////            Account account = accountRepository.findById(i).get();
////            Follow follow = Follow.builder().followerId(i).followingId(1L).build();
////            followRepository.save(follow);
////        }
////
////        Account account = accountRepository.findById(10003L).get();
////        for (long i = 2L; i < 100; i++) {
////            ArtWorks artWorks = artWorkRepository.findById(i).get();
////            ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
////            artWorkBookMarkRepository.save(artWorkBookMark);
////        }
//
//    }
//
//
//}
