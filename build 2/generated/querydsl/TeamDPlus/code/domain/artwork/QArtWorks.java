package TeamDPlus.code.domain.artwork;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtWorks is a Querydsl query type for ArtWorks
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtWorks extends EntityPathBase<ArtWorks> {

    private static final long serialVersionUID = -2028795441L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtWorks artWorks = new QArtWorks("artWorks");

    public final TeamDPlus.code.domain.QBaseEntity _super = new TeamDPlus.code.domain.QBaseEntity(this);

    public final TeamDPlus.code.domain.account.QAccount account;

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.sql.Timestamp> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMaster = createBoolean("isMaster");

    //inherited
    public final DateTimePath<java.sql.Timestamp> modified = _super.modified;

    public final BooleanPath scope = createBoolean("scope");

    public final TeamDPlus.code.domain.account.QSpecialty specialty;

    public final StringPath title = createString("title");

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final StringPath workEnd = createString("workEnd");

    public final StringPath workStart = createString("workStart");

    public QArtWorks(String variable) {
        this(ArtWorks.class, forVariable(variable), INITS);
    }

    public QArtWorks(Path<? extends ArtWorks> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtWorks(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtWorks(PathMetadata metadata, PathInits inits) {
        this(ArtWorks.class, metadata, inits);
    }

    public QArtWorks(Class<? extends ArtWorks> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.specialty = inits.isInitialized("specialty") ? new TeamDPlus.code.domain.account.QSpecialty(forProperty("specialty")) : null;
    }

}

