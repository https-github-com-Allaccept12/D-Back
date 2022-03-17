package TeamDPlus.code.domain.post.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLikes is a Querydsl query type for PostLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLikes extends EntityPathBase<PostLikes> {

    private static final long serialVersionUID = 742210173L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLikes postLikes = new QPostLikes("postLikes");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TeamDPlus.code.domain.post.QPost post;

    public QPostLikes(String variable) {
        this(PostLikes.class, forVariable(variable), INITS);
    }

    public QPostLikes(Path<? extends PostLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLikes(PathMetadata metadata, PathInits inits) {
        this(PostLikes.class, metadata, inits);
    }

    public QPostLikes(Class<? extends PostLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.post = inits.isInitialized("post") ? new TeamDPlus.code.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

