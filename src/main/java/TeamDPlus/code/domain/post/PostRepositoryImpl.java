package TeamDPlus.code.domain.post;

import TeamDPlus.code.domain.post.like.QPostLikes;
import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.domain.post.tag.QPostTag;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.like.QArtWorkLikes.artWorkLikes;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.bookmark.QPostBookMark.postBookMark;
import static TeamDPlus.code.domain.post.like.QPostLikes.postLikes;
import static TeamDPlus.code.domain.post.tag.QPostTag.postTag;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // 전체 페이지 (최신순, 좋아요순)
    @Override
    public List<PostResponseDto.PostPageMain> findAllPostOrderByCreatedDesc(Long lastPostId, Pageable pageable, PostBoard board, int sortSign, String category) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.category,
                        post.content,
                        post.created,
                        post.isSelected,
                        postLikes.count()
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastPostId(lastPostId), post.board.eq(board),
                        isCategory(category))
                .groupBy(post.id)
                .orderBy(isPostSort(sortSign))
                .fetch();
    }

    // 상세페이지 서브 정보
    @Override
    public PostResponseDto.PostSubDetail findByPostSubDetail(Long postId) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.PostSubDetail.class,
                        post.id,
                        account.id,
                        account.profileImg,
                        account.nickname,
                        post.title,
                        post.content,
                        post.view,
                        post.category,
                        post.created,
                        post.modified,
                        postLikes.count()
                ))
                .from(post)
                .groupBy(post.id)
                .innerJoin(post.account, account)
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public List<PostResponseDto.PostPageMain> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable, PostBoard board) {
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastPostId(lastPostId), post.board.eq(board),
                        post.title.contains(keyword)
                                .or(post.title.contains(keyword))
                                .or(post.account.nickname.contains(keyword))
                                .or(post.content.contains(keyword))
                                .or(postTag.hashTag.contains(keyword)))
                .orderBy(post.created.desc())
                .fetch();
    }


    // 좋아요 + 조회수 탑 10
   @Override
    public List<PostResponseDto.PostPageMain> findPostByMostViewAndMostLike() {
        List<PostResponseDto.PostPageMain> result = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.content,
                        post.category,
                        post.created,
                        post.isSelected,
                        postLikes.id.count()
                ))
                .from(post)
                .join(post.account, account)
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(0)
                .limit(10)
                .groupBy(post.id)
                .orderBy(postLikes.count().desc(), post.view.desc())
                .fetch();
        return result;
    }

    public BooleanExpression isLastPostId(Long lastPostId){
        return lastPostId != 0 ? post.id.lt(lastPostId) : null;
    }
    private OrderSpecifier<?> isPostSort(int sortSign) {
        return sortSign == 1 ? post.created.desc() : postLikes.count().desc();
    }
    private BooleanExpression isCategory(String category) {
        return category.isEmpty() ? null : post.category.eq(category);
    }
}
