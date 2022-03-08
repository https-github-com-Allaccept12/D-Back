package TeamDPlus.code.domain.artwork;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    public ArtWorkComment(String content, Account account, ArtWorks artWorks) {
        this.content = content;
        this.account = account;
        this.artWorks = artWorks;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}










