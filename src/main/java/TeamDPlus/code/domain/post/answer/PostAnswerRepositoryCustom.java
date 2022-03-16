package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;

import java.util.List;

public interface PostAnswerRepositoryCustom {

    List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId);

}
