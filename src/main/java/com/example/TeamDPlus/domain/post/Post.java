package com.example.TeamDPlus.domain.post;


import com.example.TeamDPlus.domain.BaseEntity;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    @Column(columnDefinition = "TINYINT default 0")
    private boolean isSelected;

    @Enumerated(EnumType.STRING)
    private PostBoard board;


    @Builder
    public Post(final String title, final String content, final String category,
                final Account account, final Long view, final boolean isSelected, final PostBoard board) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.account = account;
        this.view = view;
        this.isSelected = isSelected;
        this.board = board;
    }

    public void addViewCount() {
        this.view += 1L;
    }

    public void updatePost(PostRequestDto.PostUpdate dto){
        this.title = dto.getTitle();
        this.category = dto.getCategory();
        this.content = dto.getContent();
    }
    public static Post of(Account account, PostRequestDto.PostCreate dto) {
        return Post.builder()
                .account(account)
                .category(dto.getCategory())
                .content(dto.getContent())
                .title(dto.getTitle())
                .board(dto.getBoard())
                .build();
    }

    public void doIsSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }
}
