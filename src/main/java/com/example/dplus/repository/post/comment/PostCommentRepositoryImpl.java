package com.example.dplus.repository.post.comment;

import com.example.dplus.dto.response.AccountResponseDto.MyComment;
import com.example.dplus.dto.response.PostResponseDto.PostComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.post.QPostComment.postComment;
import static com.example.dplus.domain.post.QPostCommentLikes.postCommentLikes;

@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostComment> findPostCommentByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(PostComment.class,
                        postComment.account.id,
                        postComment.account.nickname,
                        postComment.account.profileImg,
                        postComment.id,
                        postComment.content,
                        postComment.modified,
                        postCommentLikes.count()
                ))
                .from(postComment)
                .leftJoin(postCommentLikes).on(postComment.id.eq(postCommentLikes.postComment.id))
                .where(postComment.post.id.eq(postId))
                .groupBy(postComment.id)
                .orderBy(postCommentLikes.count().desc())
                .fetch();
    }

    // 나의 댓글
    @Override
    public List<MyComment> findPostCommentByAccountId(Long accountId, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.constructor(MyComment.class,
                        postComment.id,
                        postComment.content,
                        postCommentLikes.count(),
                        postComment.created,
                        postComment.modified,
                        account.profileImg,
                        postComment.post.id,
                        postComment.post.title
                ))
                .from(postComment)
                .join(postComment.account, account).on(account.id.eq(accountId))
                .leftJoin(postCommentLikes).on(postComment.id.eq(postCommentLikes.postComment.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(postComment.id)
                .orderBy(postCommentLikes.count().desc())
                .fetch();
    }
}
