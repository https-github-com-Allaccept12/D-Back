package TeamDPlus.code.domain.post.comment;

import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;

import java.util.List;

public interface PostCommentRepositoryCustom {

    List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId);

    List<AccountResponseDto.MyComment> findPostCommentByAccountId(Long accountId);
}
