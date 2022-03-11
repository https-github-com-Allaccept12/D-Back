package TeamDPlus.code.domain.artwork.comment;


import TeamDPlus.code.domain.artwork.QArtWorks;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.comment.QArtWorkComment.artWorkComment;

@RequiredArgsConstructor
public class ArtWorkCommentRepositoryImpl implements ArtWorkCommentRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkComment> findArtWorkCommentByArtWorksId(Long artWorkId) {
        return queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkComment.class,
                        artWorkComment.account.id,
                        artWorkComment.id,
                        artWorkComment.content,
                        artWorkComment.modified
                        ))
                .from(artWorkComment)
                .join(artWorks).on(artWorks.id.eq(artWorkId))
                .fetch();
    }
}
