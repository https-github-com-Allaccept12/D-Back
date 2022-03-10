package TeamDPlus.code.domain.artwork.image;


import TeamDPlus.code.domain.artwork.ArtWorks;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "artwork_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtWorks artWorks;

    @Column(nullable = false)
    private String artworkImg;

    private boolean thumbnail;

    @Builder
    public ArtWorkImage(final ArtWorks artWorks,final String artworkImg,final boolean thumbnail) {
        this.artWorks = artWorks;
        this.artworkImg = artworkImg;
        this.thumbnail = thumbnail;
    }

    public void updateThumbnail() {
        this.thumbnail = !this.thumbnail;

    }

}






