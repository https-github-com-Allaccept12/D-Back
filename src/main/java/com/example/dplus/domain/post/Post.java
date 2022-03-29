package com.example.dplus.domain.post;


import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostTag> postTagList = new ArrayList<>();


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
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .account(account)
                .board(dto.getBoard())
                .build();
    }

    public void doIsSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }
}
