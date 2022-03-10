package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;

@RequiredArgsConstructor
public class ArtWorkRepositoryImpl implements ArtWorkRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long accountId) {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorks.title,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.created,
                        artWorks.modified
                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .where(artWorks.isMaster.isTrue().and(artWorks.account.id.eq(accountId)))
                .fetch();
    }

    @Override
    public List<ArtWorkResponseDto.ArtworkPageMain> findByArtWorkOrderByCreatedDesc() {
        return queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtworkPageMain.class,
                        artWorks.id,
                        artWorks.account.id,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.category,
                        artWorks.created,
                        artWorks.modified))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .orderBy(artWorks.created.desc())
                .fetch();
    }
}
