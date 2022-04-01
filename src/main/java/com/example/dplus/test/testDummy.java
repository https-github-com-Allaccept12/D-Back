package com.example.dplus.test;

import com.example.dplus.CodeApplication;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.Rank;
import com.example.dplus.domain.post.*;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import com.example.dplus.repository.post.like.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class testDummy
//        implements ApplicationRunner
{

//    @Autowired
//    PostCommentRepository commentRepository;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    PostLikesRepository postLikesRepository;
//
//    @Autowired
//    PostAnswerRepository postAnswerRepository;
//
//    @Autowired
//    PostAnswerLikesRepository postAnswerLikesRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 3; i < 5003; i++) {
//            Account account = accountRepository.findById((long) i).orElse(null);
//            PostAnswer postAnswer = postAnswerRepository.findById((long) i-2).orElse(null);
//            if(i>=2503 && i <4000){
//                for (int j = 1; j < 4; j++){
//                    PostAnswerLikes postAnswerLikes = PostAnswerLikes.builder()
//                            .account(account)
//                            .postAnswer(postAnswer)
//                            .build();
//                    postAnswerLikesRepository.save(postAnswerLikes);
//                }
//            }
//            if(i>=4000 && i<5003){
//                PostAnswerLikes postAnswerLikes = PostAnswerLikes.builder()
//                        .account(account)
//                        .postAnswer(postAnswer)
//                        .build();
//                postAnswerLikesRepository.save(postAnswerLikes);
//            }
//            if(i>=5003 && i<6000){
//                for (int j = 1; j < 4; j++){
//                    PostLikes postLikes = PostLikes.builder()
//                            .account(account)
//                            .post(post)
//                            .build();
//                    postLikesRepository.save(postLikes);
//
//                }
//            }
//            if(i>=6000 && i<7503){
//                for (int j = 1; j < 3; j++){
//                    PostLikes postLikes = PostLikes.builder()
//                            .account(account)
//                            .post(post)
//                            .build();
//                    postLikesRepository.save(postLikes);
//                }
//            }
//            if(i>=7503 && i <8000){
//                for (int j = 1; j < 4; j++){
//                    PostLikes postLikes = PostLikes.builder()
//                            .account(account)
//                            .post(post)
//                            .build();
//                    postLikesRepository.save(postLikes);
//                }
//            }
//            if(i>=8001 && i<10003){
//                for (int j = 1; j < 3; j++){
//                    PostLikes postLikes = PostLikes.builder()
//                            .account(account)
//                            .post(post)
//                            .build();
//                    postLikesRepository.save(postLikes);
//                }
//            }
// }


}
