package TeamDPlus.code.domain.artwork.bookmark;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtWorkBookMark is a Querydsl query type for ArtWorkBookMark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtWorkBookMark extends EntityPathBase<ArtWorkBookMark> {

    private static final long serialVersionUID = -108768382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtWorkBookMark artWorkBookMark = new QArtWorkBookMark("artWorkBookMark");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final TeamDPlus.code.domain.artwork.QArtWorks artWorks;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QArtWorkBookMark(String variable) {
        this(ArtWorkBookMark.class, forVariable(variable), INITS);
    }

    public QArtWorkBookMark(Path<? extends ArtWorkBookMark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtWorkBookMark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtWorkBookMark(PathMetadata metadata, PathInits inits) {
        this(ArtWorkBookMark.class, metadata, inits);
    }

    public QArtWorkBookMark(Class<? extends ArtWorkBookMark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.artWorks = inits.isInitialized("artWorks") ? new TeamDPlus.code.domain.artwork.QArtWorks(forProperty("artWorks"), inits.get("artWorks")) : null;
    }

}

