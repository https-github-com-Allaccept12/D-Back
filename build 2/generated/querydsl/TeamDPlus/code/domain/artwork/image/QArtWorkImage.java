package TeamDPlus.code.domain.artwork.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtWorkImage is a Querydsl query type for ArtWorkImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtWorkImage extends EntityPathBase<ArtWorkImage> {

    private static final long serialVersionUID = -521918902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtWorkImage artWorkImage = new QArtWorkImage("artWorkImage");

    public final StringPath artworkImg = createString("artworkImg");

    public final TeamDPlus.code.domain.artwork.QArtWorks artWorks;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath thumbnail = createBoolean("thumbnail");

    public QArtWorkImage(String variable) {
        this(ArtWorkImage.class, forVariable(variable), INITS);
    }

    public QArtWorkImage(Path<? extends ArtWorkImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtWorkImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtWorkImage(PathMetadata metadata, PathInits inits) {
        this(ArtWorkImage.class, metadata, inits);
    }

    public QArtWorkImage(Class<? extends ArtWorkImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artWorks = inits.isInitialized("artWorks") ? new TeamDPlus.code.domain.artwork.QArtWorks(forProperty("artWorks"), inits.get("artWorks")) : null;
    }

}

