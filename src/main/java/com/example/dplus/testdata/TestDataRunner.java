package com.example.dplus.testdata;

import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.Rank;
import com.example.dplus.domain.post.*;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.rank.RankRepository;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.image.PostImageRepository;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 3; i < 2503; i++) {
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
//        for (int i = 2503; i < 5003; i++) {
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
//        for (int i = 5003; i < 7503; i++) {
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
//        for (int i = 7503; i < 10003; i++) {
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
//        for (int i = 3; i < 10003; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            Post post = postRepository.findById((long) i - 2).orElse(null);
//            PostAnswer postAnswer = PostAnswer.builder()
//                    .post(post)
//                    .account(account)
//                    .content("contentcontentcontentcontentcontentcontentcontentcontentcontentcontent" + i).build();
//            PostAnswer save = postAnswerRepository.save(postAnswer);
//        }
//        for (int i = 1; i < 10001; i++) {
//            Post post = postRepository.findById((long) i).orElse(null);
//            PostImage postImage1 = PostImage.builder().post(post).postImg("img_url1").build();
//            PostImage postImage2 = PostImage.builder().post(post).postImg("img_url2").build();
//            PostImage postImage3 = PostImage.builder().post(post).postImg("img_url3").build();
//            postImageRepository.save(postImage1);
//            postImageRepository.save(postImage2);
//            postImageRepository.save(postImage3);
//        }
//        for (int i = 1; i < 100; i++) {
//            Post post = postRepository.findById((long) i).orElse(null);
//            Account account = accountRepository.findById((long) 1).orElse(null);
//            PostBookMark postBookMark = PostBookMark.builder().post(post).account(account).build();
//            postBookMarkRepository.save(postBookMark);
//        }
    }

}
