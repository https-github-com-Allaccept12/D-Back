package com.example.dplus.domain.post;


import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private Long view;

    private Boolean isSelected;

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<PostTag> postTagList = new ArrayList<>();

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<PostLikes> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<PostComment> postCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<PostAnswer> postAnswerList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PostBoard board;

    @Builder
    public Post(final String title, final String content, final String category,
                final Account account, final PostBoard board) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.account = account;
        this.board = board;
    }

    @PrePersist
    public void prePersist() {
        this.view = this.view == null ? 0 : this.view;
        this.isSelected = this.isSelected != null && this.isSelected;

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
