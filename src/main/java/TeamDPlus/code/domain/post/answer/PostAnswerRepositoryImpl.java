package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.answer.QPostAnswer.postAnswer;

@RequiredArgsConstructor
public class PostAnswerRepositoryImpl implements PostAnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId) {
//        return null;
        return jpaQueryFactory
                .select(Projections.constructor(PostResponseDto.PostAnswer.class,
                        postAnswer.account.id,
                        postAnswer.account.nickname,
                        postAnswer.account.profileImg,
                        postAnswer.id,
                        postAnswer.content,
                        postAnswer.modified
                ))
                .from(postAnswer)
                .join(post).on(post.id.eq(postId))
                .fetch();
    }

}
