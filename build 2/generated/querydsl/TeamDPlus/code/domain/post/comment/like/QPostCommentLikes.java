package TeamDPlus.code.domain.post.comment.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentLikes is a Querydsl query type for PostCommentLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentLikes extends EntityPathBase<PostCommentLikes> {

    private static final long serialVersionUID = -1712180115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentLikes postCommentLikes = new QPostCommentLikes("postCommentLikes");

    public final TeamDPlus.code.domain.QBaseEntity _super = new TeamDPlus.code.domain.QBaseEntity(this);

    public final TeamDPlus.code.domain.account.QAccount account;

    //inherited
    public final DateTimePath<java.sql.Timestamp> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> modified = _super.modified;

    public final TeamDPlus.code.domain.post.comment.QPostComment postComment;

    public QPostCommentLikes(String variable) {
        this(PostCommentLikes.class, forVariable(variable), INITS);
    }

    public QPostCommentLikes(Path<? extends PostCommentLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentLikes(PathMetadata metadata, PathInits inits) {
        this(PostCommentLikes.class, metadata, inits);
    }

    public QPostCommentLikes(Class<? extends PostCommentLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.postComment = inits.isInitialized("postComment") ? new TeamDPlus.code.domain.post.comment.QPostComment(forProperty("postComment"), inits.get("postComment")) : null;
    }

}

