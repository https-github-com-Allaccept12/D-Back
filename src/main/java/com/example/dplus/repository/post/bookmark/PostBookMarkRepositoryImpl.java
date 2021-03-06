package com.example.dplus.repository.post.bookmark;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.dplus.domain.post.QPostBookMark.postBookMark;

@RequiredArgsConstructor
public class PostBookMarkRepositoryImpl implements PostBookMarkRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 북마크 눌렀는지 체크
    @Override
    public boolean existByAccountIdAndPostId(Long accountId, Long postId) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(postBookMark)
                .where(postBookMark.account.id.eq(accountId).and(postBookMark.post.id.eq(postId)))
                .fetchFirst();
        return fetchOne!=null;
    }
}
