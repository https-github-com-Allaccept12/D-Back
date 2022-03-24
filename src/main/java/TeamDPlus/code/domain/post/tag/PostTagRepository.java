package TeamDPlus.code.domain.post.tag;

import TeamDPlus.code.dto.common.CommonDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    void deleteAllByPostId(Long postId);

    List<PostTag> findPostTagsByPostId(Long postId);
}
