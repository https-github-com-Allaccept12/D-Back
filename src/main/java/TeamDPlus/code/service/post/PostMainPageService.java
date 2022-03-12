package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.PostRequestDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostMainPageService {
    Page<PostResponseDto.PostPageMain> showPostMain(Long accountId, Long postId);
    Long createPost(Account account, PostRequestDto.PostCreateAndUpdate dto);
}
