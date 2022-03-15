package TeamDPlus.code.domain.post;

import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.domain.post.tag.QPostTag;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.tag.QPostTag.postTag;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    // 전체 페이지
    @Override
    public Page<PostResponseDto.PostPageMain> findAllPost(Long lastPostId, Pageable pageable) {
        List<PostResponseDto.PostPageMain> fetch = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.category,
                        post.content,
                        post.created
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(post.id.lt(12))
                .fetch();

        int count = fetch.size();
        return new PageImpl<>(fetch, pageable, count);
    }

    // 글 상세 페이지
    @Override
    public PostResponseDto.PostDetailPage findDetailPost(Long postId){
//        return queryFactory
//                .select(Projections.constructor(PostResponseDto.PostDetailPage.class,
//                        post.id, //
//                        post.content,//
//                        post.created,//
//                        post.title,//
//                        post.category,//
//                        post.created,//
//                        post.modified, //
//                        postImage.postImg,//
//                        account.id,//
//                        account.nickname,//
//                        account.profileImg,//
//                        postLikes.post,//
//                        post.view,//
//                        postComment.post.id,//
//                        postComment.account.nickname,//
//                        postComment.content,//
//                        postComment.created,//
//                        postComment.modified,//
//                        postComment.is_selected//
//                        ))
//                .from(post)
//                .join(account).on(account.id.eq(post.account.id))
//                .leftJoin(postImage).on(postImage.post.id.eq(post.id))
//                .leftJoin(postComment).on(postComment.post.id.eq(post.id))
//                .fetchOne();
        return null;
    }

    @Override
    public Page<PostResponseDto.PostPageMain> findPostBySearchKeyWord(String keyword, Long lastPostId, Pageable pageable) {
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
                        postTag.hashTag
                        ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postTag).on(postTag.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastPostId(lastPostId),
                        post.title.contains(keyword)
                                .or(post.title.contains(keyword))
                                .or(post.account.nickname.contains(keyword))
                                .or(post.content.contains(keyword))
                                .or(postTag.hashTag.contains(keyword)))
                .orderBy(post.created.desc())
                .fetch();
        return new PageImpl<>(result,pageable,result.size());
    }

    public BooleanExpression isLastPostId(Long lastPostId){
        return lastPostId != 0 ? post.id.lt(lastPostId) : null;
    }
}