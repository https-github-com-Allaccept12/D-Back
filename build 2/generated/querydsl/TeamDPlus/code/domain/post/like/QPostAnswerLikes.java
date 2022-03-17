package TeamDPlus.code.domain.post.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostAnswerLikes is a Querydsl query type for PostAnswerLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostAnswerLikes extends EntityPathBase<PostAnswerLikes> {

    private static final long serialVersionUID = 2050645151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostAnswerLikes postAnswerLikes = new QPostAnswerLikes("postAnswerLikes");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TeamDPlus.code.domain.post.answer.QPostAnswer postAnswer;

    public QPostAnswerLikes(String variable) {
        this(PostAnswerLikes.class, forVariable(variable), INITS);
    }

    public QPostAnswerLikes(Path<? extends PostAnswerLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostAnswerLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostAnswerLikes(PathMetadata metadata, PathInits inits) {
        this(PostAnswerLikes.class, metadata, inits);
    }

    public QPostAnswerLikes(Class<? extends PostAnswerLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.postAnswer = inits.isInitialized("postAnswer") ? new TeamDPlus.code.domain.post.answer.QPostAnswer(forProperty("postAnswer"), inits.get("postAnswer")) : null;
    }

}

