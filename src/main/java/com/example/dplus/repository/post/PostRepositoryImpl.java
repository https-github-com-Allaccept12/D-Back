package com.example.dplus.repository.post;

import com.example.dplus.domain.post.Post;
import com.example.dplus.domain.post.PostBoard;
import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.post.QPost.post;
import static com.example.dplus.domain.post.QPostBookMark.postBookMark;
import static com.example.dplus.domain.post.QPostLikes.postLikes;
import static com.example.dplus.domain.post.QPostTag.postTag;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // 전체 페이지 최신순
    @Override
    public List<Post> findAllPostOrderByCreatedDesc(Long lastPostId,String board, String category) {
        return queryFactory
                .selectFrom(post)
                .innerJoin(post.account,account)
                .limit(12)
                .where(isLastPostId(lastPostId),
                        post.board.eq(PostBoard.valueOf(board)),
                        isCategory(category))
                .groupBy(post.id)
                .orderBy(post.created.desc())
                .fetch();
    }

    @Override
    public List<Post> findAllPostOrderByLikeDesc(Pageable pageable, String board, String category) {
        return queryFactory
                .selectFrom(post)
                .innerJoin(post.account,account)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(post.board.eq(PostBoard.valueOf(board)),
                        isCategory(category))
                .groupBy(post.id)
                .orderBy(post.postLikeList.size().desc(),post.created.desc())
                .fetch();

    }
    @Override
    public List<PostResponseDto.PostPageMain> findPostBySearchKeyWord(String keyword, Long lastPostId,String board) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.content,
                        post.category,
                        post.created,
                        postTag.hashTag
                        ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postTag).on(postTag.post.eq(post))
                .limit(12)
                .where(isLastPostId(lastPostId),post.board.eq(PostBoard.valueOf(board)),
                        (post.title.contains(keyword)
                                .or(post.account.nickname.contains(keyword))
                                .or(post.content.contains(keyword))
                                .or(postTag.hashTag.contains(keyword))))
                .orderBy(post.created.desc())
                .fetch();
    }

    // 좋아요 + 조회수 탑 10
    @Override
    public List<Post> findPostByMostViewAndMostLike() {
        return queryFactory
                .selectFrom(post)
                .innerJoin(post.account)
                .limit(10)
                .groupBy(post.id)
                .orderBy(post.postLikeList.size().desc(),post.created.desc())
                .fetch();
    }

    public BooleanExpression isLastPostId(Long lastPostId){
        return lastPostId != 0 ? post.id.lt(lastPostId) : null;
    }

    // 유사한 질문
    @Override
    public List<Post> findBySimilarPost(String category, String board, Long postId) {
        return queryFactory
                .selectFrom(post)
                .join(post.account, account)
                .offset(0)
                .limit(5)
                .where(post.category.eq(category),
                        post.board.eq(PostBoard.valueOf(board)),
                        post.id.ne(postId))
                .groupBy(post.id)
                .orderBy(post.postLikeList.size().desc())
                .fetch();
    }

    // 나의 질문
    @Override
    public List<AccountResponseDto.MyPost> findPostByAccountIdAndBoard(Long accountId, String board, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(AccountResponseDto.MyPost.class,
                        post.id,
                        post.title,
                        post.content,
                        postLikes.count(),
                        post.created,
                        post.modified,
                        account.profileImg))
                .from(post)
                .join(post.account, account).on(account.id.eq(accountId))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(post.board.eq(PostBoard.valueOf(board)))
                .groupBy(post.id)
                .orderBy(post.created.desc())
                .fetch();
    }

    // 내가 스크랩한 글
    @Override
    public List<AccountResponseDto.MyPost> findPostBookMarkByAccountId(Long accountId, String board, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(AccountResponseDto.MyPost.class,
                        post.id,
                        post.title,
                        post.content,
                        postLikes.count(),
                        post.created,
                        post.modified,
                        post.account.profileImg
                ))
                .from(post)
                .join(postBookMark).on(postBookMark.post.eq(post))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(postBookMark.account.id.eq(accountId), post.board.eq(PostBoard.valueOf(board)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .orderBy(post.created.desc())
                .fetch();
    }
    private BooleanExpression isCategory(String category) {
        return category.isEmpty() ? null : post.category.eq(category);
    }
}
