package com.example.dplus.domain.artwork;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorkImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artwork_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id",nullable = false)
    private ArtWorks artWorks;

    @Column(nullable = false)
    private String artworkImg;


    @Builder
    public ArtWorkImage(final ArtWorks artWorks,final String artworkImg) {
        this.artWorks = artWorks;
        this.artworkImg = artworkImg;
    }


}






