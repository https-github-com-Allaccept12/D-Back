package TeamDPlus.code.service.post;

import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostMainPageService {
    Page<PostResponseDto.PostPageMain> showPostMain(Long accountId, Long postId);
}
