package TeamDPlus.code.domain.artwork.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtWorkLikes is a Querydsl query type for ArtWorkLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtWorkLikes extends EntityPathBase<ArtWorkLikes> {

    private static final long serialVersionUID = 1612827727L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtWorkLikes artWorkLikes = new QArtWorkLikes("artWorkLikes");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final TeamDPlus.code.domain.artwork.QArtWorks artWorks;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QArtWorkLikes(String variable) {
        this(ArtWorkLikes.class, forVariable(variable), INITS);
    }

    public QArtWorkLikes(Path<? extends ArtWorkLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtWorkLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtWorkLikes(PathMetadata metadata, PathInits inits) {
        this(ArtWorkLikes.class, metadata, inits);
    }

    public QArtWorkLikes(Class<? extends ArtWorkLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.artWorks = inits.isInitialized("artWorks") ? new TeamDPlus.code.domain.artwork.QArtWorks(forProperty("artWorks"), inits.get("artWorks")) : null;
    }

}

