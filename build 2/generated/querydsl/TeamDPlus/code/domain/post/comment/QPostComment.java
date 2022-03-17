package TeamDPlus.code.domain.post.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostComment is a Querydsl query type for PostComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostComment extends EntityPathBase<PostComment> {

    private static final long serialVersionUID = 178809400L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostComment postComment = new QPostComment("postComment");

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

    public QPostComment(String variable) {
        this(PostComment.class, forVariable(variable), INITS);
    }

    public QPostComment(Path<? extends PostComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostComment(PathMetadata metadata, PathInits inits) {
        this(PostComment.class, metadata, inits);
    }

    public QPostComment(Class<? extends PostComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.post = inits.isInitialized("post") ? new TeamDPlus.code.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

