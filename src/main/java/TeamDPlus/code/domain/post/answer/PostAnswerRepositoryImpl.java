package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostAnswerRepositoryImpl implements PostAnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId) {
        return null;
    }

}
