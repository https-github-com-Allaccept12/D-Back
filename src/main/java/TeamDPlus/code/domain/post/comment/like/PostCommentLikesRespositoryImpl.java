package TeamDPlus.code.domain.post.comment.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static TeamDPlus.code.domain.post.comment.like.QPostCommentLikes.postCommentLikes;

@RequiredArgsConstructor
public class PostCommentLikesRespositoryImpl implements PostCommentLikesRespositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 디플 정보공유 포스트-코멘트 좋아요 눌렀는지 체크
    @Override
    public boolean existByAccountIdAndPostCommentId(Long accountId, Long postCommentId) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(postCommentLikes)
                .where(postCommentLikes.account.id.eq(accountId).and(postCommentLikes.postComment.id.eq(postCommentId)))
                .fetchFirst();
        return fetchOne!=null;
    }
}
