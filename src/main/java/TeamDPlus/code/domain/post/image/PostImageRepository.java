package TeamDPlus.code.domain.post.image;

import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    void deleteAllByPostId(Long postId);
    List<PostImage> findByPostId(Long postId);
}
