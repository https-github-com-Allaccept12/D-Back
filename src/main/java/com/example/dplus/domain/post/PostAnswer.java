package com.example.dplus.domain.post;

import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TINYINT default 0")
    private boolean isSelected;

    @Builder
    public PostAnswer(final Account account, final Post post, final String content, final boolean isSelected) {
        this.account = account;
        this.post = post;
        this.content = content;
        this.isSelected = isSelected;
    }

    public void updateAnswer(final String content) {
        this.content = content;
    }

    public void doIsSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }
}
