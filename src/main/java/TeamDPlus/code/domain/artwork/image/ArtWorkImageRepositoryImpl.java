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
    public List<String> findByAllImageUrl(Long artworkId) {
        return queryFactory
                .select(artWorkImage.artworkImg)
                .from(artWorkImage)
                .where(artWorkImage.artWorks.id.eq(artworkId))
                .fetch();


    }
}
