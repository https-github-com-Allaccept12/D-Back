package TeamDPlus.code.domain.post.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static TeamDPlus.code.domain.post.like.QPostLikes.postLikes;

@RequiredArgsConstructor
public class PostLikesRepositoryImpl implements PostLikesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existByAccountIdAndPostId(Long accountId, Long postId) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(postLikes)
                .where(postLikes.account.id.eq(accountId).and(postLikes.post.id.eq(postId)))
                .fetchFirst();
        return fetchOne!=null;
    }
}
