package TeamDPlus.code.domain.artwork.image;

import TeamDPlus.code.domain.artwork.QArtWorks;
import TeamDPlus.code.dto.common.CommonDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;

@RequiredArgsConstructor
public class ArtWorkImageRepositoryImpl implements ArtworkImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommonDto.ImgUrlDto> findArtWorkImageByTopView(Long accountId) {
        return queryFactory
                .select(Projections.constructor(CommonDto.ImgUrlDto.class,
                        artWorkImage.artworkImg
                        ))
                .from(artWorkImage)
                .leftJoin(artWorkImage.artWorks, artWorks).on(artWorks.scope.isTrue())
                .limit(2)
                .orderBy(artWorks.view.desc())
                .fetch();
    }

    @Override
    public ArtWorkImage findByThumbnail(Long artWorksId) {
        return queryFactory
                .selectFrom(artWorkImage)
                .where(artWorkImage.artWorks.id.eq(artWorksId).and(artWorkImage.thumbnail.isTrue()))
                .fetchOne();


    }
}
