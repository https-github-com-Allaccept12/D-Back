package TeamDPlus.code.domain.post.comment;

import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition = "TINYINT default 0")
    private int is_selected;

    @Builder
    public PostComment(final String content,final Account account,final Post post, int is_selected) {
        this.content = content;
        this.account = account;
        this.post = post;
        this.is_selected = is_selected;
    }

    public void updateComment(final String content) {
        this.content = content;
    }
}
