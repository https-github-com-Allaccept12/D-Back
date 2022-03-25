package TeamDPlus.code.domain.post;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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
        this.isSelected = dto.is_selected();
    }
    public static Post of(Account account, PostRequestDto.PostCreate dto) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .account(account)
                .isSelected(dto.is_selected())
                .board(dto.getBoard())
                .build();
    }

    public void doIsSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }
}
