package com.example.dplus.repository.post.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.post.QPostCommentLikes.postCommentLikes;

@RequiredArgsConstructor
public class PostCommentLikesRepositoryImpl implements PostCommentLikesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

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
