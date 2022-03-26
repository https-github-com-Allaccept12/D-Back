package com.example.dplus.domain.post.like;

import com.example.dplus.domain.post.answer.PostAnswer;
import com.example.dplus.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAnswerLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_answer_id")
    private PostAnswer postAnswer;

    @Builder
    public PostAnswerLikes(final Account account,final PostAnswer postAnswer) {
        this.account = account;
        this.postAnswer = postAnswer;
    }

}
