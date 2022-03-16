package TeamDPlus.code.domain.post.bookmark;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public PostBookMark(final Post post, final Account account) {
        this.post = post;
        this.account = account;
    }
}
