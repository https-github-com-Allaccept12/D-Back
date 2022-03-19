package TeamDPlus.code.domain.post.comment;

import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.post.comment.QPostComment.postComment;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.comment.like.QPostCommentLikes.postCommentLikes;

@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(PostResponseDto.PostComment.class,
                        postComment.account.id,
                        postComment.account.nickname,
                        postComment.account.profileImg,
                        postComment.id,
                        postComment.content,
                        postComment.modified,
                        postCommentLikes.count()
                ))
                .from(postComment)
                .join(post).on(post.id.eq(postId))
                .leftJoin(postCommentLikes).on(postComment.id.eq(postCommentLikes.postComment.id))
                .groupBy(postComment.id) // groupBy로 묶어줘야 성능 올라감
                .orderBy(postCommentLikes.count().desc())
                .fetch();
    }
}
