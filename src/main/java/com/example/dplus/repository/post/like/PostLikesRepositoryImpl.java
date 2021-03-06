package com.example.dplus.repository.post.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.post.QPostLikes.postLikes;


@RequiredArgsConstructor
public class PostLikesRepositoryImpl implements PostLikesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 좋아요 눌렀는지 체크
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
