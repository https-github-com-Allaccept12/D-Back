package TeamDPlus.code.domain.post.bookmark;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostBookMark is a Querydsl query type for PostBookMark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostBookMark extends EntityPathBase<PostBookMark> {

    private static final long serialVersionUID = -552509514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostBookMark postBookMark = new QPostBookMark("postBookMark");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TeamDPlus.code.domain.post.QPost post;

    public QPostBookMark(String variable) {
        this(PostBookMark.class, forVariable(variable), INITS);
    }

    public QPostBookMark(Path<? extends PostBookMark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostBookMark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostBookMark(PathMetadata metadata, PathInits inits) {
        this(PostBookMark.class, metadata, inits);
    }

    public QPostBookMark(Class<? extends PostBookMark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.post = inits.isInitialized("post") ? new TeamDPlus.code.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

