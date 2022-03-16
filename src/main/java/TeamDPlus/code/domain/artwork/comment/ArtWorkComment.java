package TeamDPlus.code.domain.artwork.comment;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorkComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtWorks artWorks;

    @Builder
    public ArtWorkComment(final String content,final Account account,final ArtWorks artWorks) {
        this.content = content;
        this.account = account;
        this.artWorks = artWorks;
    }

    public void updateComment(final String content) {
        this.content = content;
    }

}










