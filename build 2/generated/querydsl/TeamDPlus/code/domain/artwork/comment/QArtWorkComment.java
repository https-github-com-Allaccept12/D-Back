package TeamDPlus.code.domain.artwork.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtWorkComment is a Querydsl query type for ArtWorkComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtWorkComment extends EntityPathBase<ArtWorkComment> {

    private static final long serialVersionUID = -50656758L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtWorkComment artWorkComment = new QArtWorkComment("artWorkComment");

    public final TeamDPlus.code.domain.QBaseEntity _super = new TeamDPlus.code.domain.QBaseEntity(this);

    public final TeamDPlus.code.domain.account.QAccount account;

    public final TeamDPlus.code.domain.artwork.QArtWorks artWorks;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.sql.Timestamp> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> modified = _super.modified;

    public QArtWorkComment(String variable) {
        this(ArtWorkComment.class, forVariable(variable), INITS);
    }

    public QArtWorkComment(Path<? extends ArtWorkComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtWorkComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtWorkComment(PathMetadata metadata, PathInits inits) {
        this(ArtWorkComment.class, metadata, inits);
    }

    public QArtWorkComment(Class<? extends ArtWorkComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.artWorks = inits.isInitialized("artWorks") ? new TeamDPlus.code.domain.artwork.QArtWorks(forProperty("artWorks"), inits.get("artWorks")) : null;
    }

}

