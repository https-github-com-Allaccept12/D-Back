package com.example.dplus.domain.artwork;


import com.example.dplus.domain.account.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorkBookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id",nullable = false)
    private ArtWorks artWorks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    @Builder
    public ArtWorkBookMark(final ArtWorks artWorks,final Account account) {
        this.artWorks = artWorks;
        this.account = account;
    }
}
