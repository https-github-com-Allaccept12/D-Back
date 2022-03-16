package TeamDPlus.code.domain.artwork.like;


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
public class ArtWorkLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtWorks artWorks;

    @Builder
    public ArtWorkLikes(final Account account,final ArtWorks artWorks) {
        this.account = account;
        this.artWorks = artWorks;
    }
}









