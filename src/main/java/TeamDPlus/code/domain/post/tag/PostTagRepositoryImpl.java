package TeamDPlus.code.domain.post.tag;

import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.post.tag.QPostTag.postTag;
import static TeamDPlus.code.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostTagRepositoryImpl implements PostTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommonDto.PostTagDto> findPostTagListByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(CommonDto.PostTagDto.class,
                        postTag.hashTag))
                .from(postTag)
                .join(post).on(post.id.eq(postTag.post.id))
                .fetch();
    }
}
