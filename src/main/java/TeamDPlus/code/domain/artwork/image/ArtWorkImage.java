package TeamDPlus.code.domain.artwork.image;


import TeamDPlus.code.domain.artwork.ArtWorks;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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

    @Column(columnDefinition = "TINYINT default 0")
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

    public void updateImage(String img) {
        this.artworkImg = img;
    }

}






