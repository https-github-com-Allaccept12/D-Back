package TeamDPlus.code.domain.post.answer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostAnswer is a Querydsl query type for PostAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostAnswer extends EntityPathBase<PostAnswer> {

    private static final long serialVersionUID = -963809130L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostAnswer postAnswer = new QPostAnswer("postAnswer");

    public final TeamDPlus.code.domain.QBaseEntity _super = new TeamDPlus.code.domain.QBaseEntity(this);

    public final TeamDPlus.code.domain.account.QAccount account;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.sql.Timestamp> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSelected = createBoolean("isSelected");

    //inherited
    public final DateTimePath<java.sql.Timestamp> modified = _super.modified;

    public final TeamDPlus.code.domain.post.QPost post;

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public QPostAnswer(String variable) {
        this(PostAnswer.class, forVariable(variable), INITS);
    }

    public QPostAnswer(Path<? extends PostAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostAnswer(PathMetadata metadata, PathInits inits) {
        this(PostAnswer.class, metadata, inits);
    }

    public QPostAnswer(Class<? extends PostAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.post = inits.isInitialized("post") ? new TeamDPlus.code.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

