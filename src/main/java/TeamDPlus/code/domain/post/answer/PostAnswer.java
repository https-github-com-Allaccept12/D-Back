package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    @Column(columnDefinition = "TINYINT default 0")
    private boolean isSelected;

    @Builder
    public PostAnswer(final Account account, final Post post, final String content, final Long view, final boolean isSelected) {
        this.account = account;
        this.post = post;
        this.content = content;
        this.view = view;
        this.isSelected = isSelected;
    }

    public void updateAnswer(final String content) {
        this.content = content;
    }

    public void doIsSelected(final boolean isSElected) {
        this.isSelected = isSelected;
    }
}
