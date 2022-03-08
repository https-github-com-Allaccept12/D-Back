package TeamDPlus.code.domain.post.image;


import TeamDPlus.code.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_image_id")
    private Long id;

    @Column(nullable = false)
    private String postImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostImage(String postImg, Post post) {
        this.postImg = postImg;
        this.post = post;
    }
}
