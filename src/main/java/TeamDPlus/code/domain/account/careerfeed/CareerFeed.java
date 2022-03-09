package TeamDPlus.code.domain.account.careerfeed;


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
public class CareerFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "career_feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtWorks artWorks;

    @Builder
    public CareerFeed(final Account account,final ArtWorks artWorks) {
        this.account = account;
        this.artWorks = artWorks;
    }

}


