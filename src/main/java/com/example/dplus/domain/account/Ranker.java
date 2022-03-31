package com.example.dplus.domain.account;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ranker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rankers_id")
    private Long id;

    @Column(nullable = false)
    private Long rankerId;

    @Builder
    public Ranker(Long rankerId) {
        this.rankerId = rankerId;
    }
}






