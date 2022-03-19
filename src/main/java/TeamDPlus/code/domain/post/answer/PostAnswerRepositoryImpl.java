package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.answer.QPostAnswer.postAnswer;
import static TeamDPlus.code.domain.post.like.QPostAnswerLikes.postAnswerLikes;

@RequiredArgsConstructor
public class PostAnswerRepositoryImpl implements PostAnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(PostResponseDto.PostAnswer.class,
                        postAnswer.account.id,
                        postAnswer.account.nickname,
                        postAnswer.account.profileImg,
                        postAnswer.id,
                        postAnswer.content,
                        postAnswer.modified,
                        postAnswer.isSelected,
                        postAnswerLikes.count()
                ))
                .from(postAnswer)
                .join(post).on(post.id.eq(postId))
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();
    }

    @Override
    public List<AccountResponseDto.MyAnswer> findPostAnswerByAccountId(Long accountId) {
        return jpaQueryFactory
                .select(Projections.constructor(AccountResponseDto.MyAnswer.class,
                        postAnswer.id,
                        postAnswer.content,
                        postAnswer.created,
                        postAnswer.modified,
                        postAnswerLikes.count(),
                        account.profileImg
                ))
                .from(postAnswer)
                .join(postAnswer.account, account).on(account.id.eq(accountId))
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();
    }

}
