package TeamDPlus.code.domain.artwork;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artwork_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private ArtWorks artWorks;

    private String artwork_img;

    @Builder
    public ArtWorkImage(ArtWorks artWorks, String artwork_img) {
        this.artWorks = artWorks;
        this.artwork_img = artwork_img;
        artWorks.getArtWorkImage().add(this);
    }

    public void deleteArtWorkImage() {
        artWorks.getArtWorkImage().remove(this);
    }


}






