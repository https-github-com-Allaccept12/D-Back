package com.example.TeamDPlus.domain.post.comment.like;


import com.example.TeamDPlus.domain.BaseEntity;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.post.comment.PostComment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentLikes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_comment_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostComment postComment;

    @Builder
    public PostCommentLikes(final Account account, final PostComment postComment) {
        this.account = account;
        this.postComment = postComment;
    }
}
