package com.example.dplus.domain.account;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Ranks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;

    @Column(columnDefinition = "BIGINT default 0")
    private Long rankScore;


    @Builder
    public Rank(final Long rankScore) {
        this.rankScore = rankScore;

    }



    public void upRankScore() {
        this.rankScore += 1L;
    }

    public void downRankScore() {
        this.rankScore -= 1L;
    }

}
