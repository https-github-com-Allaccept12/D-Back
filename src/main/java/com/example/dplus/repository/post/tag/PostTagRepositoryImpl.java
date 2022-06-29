package com.example.dplus.repository.post.tag;

import com.example.dplus.dto.common.CommonDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dplus.domain.post.QPost.post;
import static com.example.dplus.domain.post.QPostTag.postTag;


@RequiredArgsConstructor
public class PostTagRepositoryImpl implements PostTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommonDto.PostTagDto> findPostTagListByPostId(Long postId) {
        return jpaQueryFactory
                .select(Projections.constructor(CommonDto.PostTagDto.class,
                        postTag.hashTag))
                .from(postTag)
                .leftJoin(post).on(postTag.id.eq(postId))
                .fetch();
    }
}