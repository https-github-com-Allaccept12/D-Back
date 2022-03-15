package TeamDPlus.code.domain.account.rank;


import TeamDPlus.code.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "rank_id")
    private Long id;

//    @Column(unique = true)
//    @OneToOne(mappedBy = "rank")
//    private Account accountId;

    @Column(columnDefinition = "BIGINT default 0")
    private Long rankScore;

    @Builder
    public Rank(final Long rankScore) {
        this.rankScore = rankScore;

    }

    public void upRankScore() {
        this.rankScore += 1;
    }

    public void downRankScore() {
        this.rankScore -= 1;
    }

    public void initCount() {
        this.rankScore = 0L;
    }
}