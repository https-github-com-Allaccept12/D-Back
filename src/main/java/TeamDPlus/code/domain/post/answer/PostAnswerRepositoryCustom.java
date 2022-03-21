package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostAnswerRepositoryCustom {

    List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId);

    List<AccountResponseDto.MyAnswer> findPostAnswerByAccountId(Long accountId, Pageable pageable);

}
