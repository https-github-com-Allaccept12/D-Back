package com.example.dplus.service;

import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.rank.RankRepository;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.repository.post.image.PostImageRepository;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import com.example.dplus.repository.post.like.PostCommentLikesRepository;
import com.example.dplus.repository.post.like.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    PostAnswerRepository postAnswerRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostBookMarkRepository postBookMarkRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostLikesRepository postLikesRepository;

    @Autowired
    PostAnswerLikesRepository postAnswerLikesRepository;

    @Autowired
    PostCommentLikesRepository postCommentLikesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 1; i < 251; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = Post.builder()
//                    .title("title" + i)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i)
//                    .category("testcategory1")
//                    .account(account)
//                    .board(PostBoard.QNA)
//                    .build();
//            Post savedPost = postRepository.save(post);
//        }
//        for (int i = 251; i < 501; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = Post.builder()
//                    .title("title" + i)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i)
//                    .category("testcategory2")
//                    .account(account)
//                    .board(PostBoard.QNA)
//                    .build();
//            Post savedPost = postRepository.save(post);
//        }
//        for (int i = 501; i < 751; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = Post.builder()
//                    .title("title" + i)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i)
//                    .category("testcategory1")
//                    .account(account)
//                    .board(PostBoard.INFO)
//                    .build();
//            Post savedPost = postRepository.save(post);
//        }
//        for (int i = 751; i < 1001; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = Post.builder()
//                    .title("title" + i)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i)
//                    .category("testcategory2")
//                    .account(account)
//                    .board(PostBoard.INFO)
//                    .build();
//            Post savedPost = postRepository.save(post);
//        }
//        for (int i = 1; i < 501; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostAnswer postAnswer = PostAnswer.builder()
//                    .post(post)
//                    .account(account)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i).build();
//            PostAnswer save = postAnswerRepository.save(postAnswer);
//        }
//        for (int i = 501; i < 1001; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostComment postComment = PostComment.builder()
//                    .post(post)
//                    .account(account)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i).build();
//            PostComment save = postCommentRepository.save(postComment);
//        }
//        for (int i = 1; i < 501; i++) {
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostImage postImage1 = PostImage.builder().post(post).postImg("img_url1").build();
//            PostImage postImage2 = PostImage.builder().post(post).postImg("img_url2").build();
//            PostImage postImage3 = PostImage.builder().post(post).postImg("img_url3").build();
//            postImageRepository.save(postImage1);
//            postImageRepository.save(postImage2);
//            postImageRepository.save(postImage3);
//        }
//        for (int i = 264; i < 1001; i++) {
//            Post post = postRepository.findById((long) i).orElse(null);
//            Account account = accountRepository.findById((long) i).orElse(null);
//            PostBookMark postBookMark = PostBookMark.builder().post(post).account(account).build();
//            postBookMarkRepository.save(postBookMark);
//        }
//        for (int i = 1; i < 101; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            Account account3 = accountRepository.findById((long) 3).orElse(null);
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostLikes postLikes1 = PostLikes.builder().account(account1).post(post).build();
//            PostLikes postLikes2 = PostLikes.builder().account(account2).post(post).build();
//            PostLikes postLikes3 = PostLikes.builder().account(account3).post(post).build();
//            postLikesRepository.save(postLikes1);
//            postLikesRepository.save(postLikes2);
//            postLikesRepository.save(postLikes3);
//        }
//        for (int i = 101; i < 201; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostLikes postLikes1 = PostLikes.builder().account(account1).post(post).build();
//            PostLikes postLikes2 = PostLikes.builder().account(account2).post(post).build();
//            postLikesRepository.save(postLikes1);
//            postLikesRepository.save(postLikes2);
//        }
//        for (int i = 201; i < 301; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostLikes postLikes1 = PostLikes.builder().account(account1).post(post).build();
//            postLikesRepository.save(postLikes1);
//        }
//        for (int i = 1; i < 101; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            Account account3 = accountRepository.findById((long) 3).orElse(null);
//            PostAnswer postAnswer = postAnswerRepository.findById((long) i).orElse(null);
//            PostAnswerLikes postAnswerLikes1 = PostAnswerLikes.builder().account(account1).postAnswer(postAnswer).build();
//            PostAnswerLikes postAnswerLikes2 = PostAnswerLikes.builder().account(account2).postAnswer(postAnswer).build();
//            PostAnswerLikes postAnswerLikes3 = PostAnswerLikes.builder().account(account3).postAnswer(postAnswer).build();
//            postAnswerLikesRepository.save(postAnswerLikes1);
//            postAnswerLikesRepository.save(postAnswerLikes2);
//            postAnswerLikesRepository.save(postAnswerLikes3);
//        }
//        for (int i = 101; i < 201; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            PostAnswer postAnswer = postAnswerRepository.findById((long) i).orElse(null);
//            PostAnswerLikes postAnswerLikes1 = PostAnswerLikes.builder().account(account1).postAnswer(postAnswer).build();
//            PostAnswerLikes postAnswerLikes2 = PostAnswerLikes.builder().account(account2).postAnswer(postAnswer).build();
//            postAnswerLikesRepository.save(postAnswerLikes1);
//            postAnswerLikesRepository.save(postAnswerLikes2);
//        }
//        for (int i = 201; i < 301; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            PostAnswer postAnswer = postAnswerRepository.findById((long) i).orElse(null);
//            PostAnswerLikes postAnswerLikes1 = PostAnswerLikes.builder().account(account1).postAnswer(postAnswer).build();
//            postAnswerLikesRepository.save(postAnswerLikes1);
//        }
//        for (int i = 1; i < 101; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            Account account3 = accountRepository.findById((long) 3).orElse(null);
//            PostComment postComment = postCommentRepository.findById((long) i).orElse(null);
//            PostCommentLikes postCommentLikes1 = PostCommentLikes.builder().account(account1).postComment(postComment).build();
//            PostCommentLikes postCommentLikes2 = PostCommentLikes.builder().account(account2).postComment(postComment).build();
//            PostCommentLikes postCommentLikes3 = PostCommentLikes.builder().account(account3).postComment(postComment).build();
//            postCommentLikesRepository.save(postCommentLikes1);
//            postCommentLikesRepository.save(postCommentLikes2);
//            postCommentLikesRepository.save(postCommentLikes3);
//        }
//        for (int i = 101; i < 201; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            Account account2 = accountRepository.findById((long) 2).orElse(null);
//            PostComment postComment = postCommentRepository.findById((long) i).orElse(null);
//            PostCommentLikes postCommentLikes1 = PostCommentLikes.builder().account(account1).postComment(postComment).build();
//            PostCommentLikes postCommentLikes2 = PostCommentLikes.builder().account(account2).postComment(postComment).build();
//            postCommentLikesRepository.save(postCommentLikes1);
//            postCommentLikesRepository.save(postCommentLikes2);
//        }
//        for (int i = 201; i < 301; i++) {
//            Account account1 = accountRepository.findById((long) 1).orElse(null);
//            PostComment postComment = postCommentRepository.findById((long) i).orElse(null);
//            PostCommentLikes postCommentLikes1 = PostCommentLikes.builder().account(account1).postComment(postComment).build();
//            postCommentLikesRepository.save(postCommentLikes1);
//        }
    }

}