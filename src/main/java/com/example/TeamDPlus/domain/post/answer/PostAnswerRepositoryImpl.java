package com.example.TeamDPlus.domain.post.answer;

import com.example.TeamDPlus.dto.response.AccountResponseDto;
import com.example.TeamDPlus.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.TeamDPlus.domain.account.QAccount.account;
import static com.example.TeamDPlus.domain.post.answer.QPostAnswer.postAnswer;
import static com.example.TeamDPlus.domain.post.like.QPostAnswerLikes.postAnswerLikes;

@RequiredArgsConstructor
public class PostAnswerRepositoryImpl implements PostAnswerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostResponseDto.PostAnswer> findPostAnswerByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(PostResponseDto.PostAnswer.class,
                        postAnswer.id,
                        postAnswer.account.id,
                        postAnswer.account.nickname,
                        postAnswer.account.profileImg,
                        postAnswer.content,
                        postAnswer.modified,
                        postAnswer.isSelected,
                        postAnswerLikes.count()
                ))
                .from(postAnswer)
                .innerJoin(postAnswer.account, account)
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .where(postAnswer.post.id.eq(postId))
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();
    }

    // 나의 답글
    @Override
    public List<AccountResponseDto.MyAnswer> findPostAnswerByAccountId(Long accountId, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.constructor(AccountResponseDto.MyAnswer.class,
                        postAnswer.id,
                        postAnswer.content,
                        postAnswerLikes.count(),
                        postAnswer.created,
                        postAnswer.modified,
                        account.profileImg
                ))
                .from(postAnswer)
                .join(postAnswer.account, account).on(account.id.eq(accountId))
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();
    }

}
