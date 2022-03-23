package TeamDPlus.code.domain.post.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static TeamDPlus.code.domain.post.like.QPostAnswerLikes.postAnswerLikes;

@RequiredArgsConstructor
public class PostAnswerLikesRepositoryImpl implements PostAnswerLikesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 좋아요 눌렀는지 체크
    @Override
    public boolean existByAccountIdAndPostAnswerId(Long accountId, Long postAnswerId) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(postAnswerLikes)
                .where(postAnswerLikes.account.id.eq(accountId).and(postAnswerLikes.postAnswer.id.eq(postAnswerId)))
                .fetchFirst();
        return fetchOne!=null;
    }
}
