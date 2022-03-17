package TeamDPlus.code.domain.post.answer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostAnswerComment is a Querydsl query type for PostAnswerComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostAnswerComment extends EntityPathBase<PostAnswerComment> {

    private static final long serialVersionUID = 776167657L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostAnswerComment postAnswerComment = new QPostAnswerComment("postAnswerComment");

    public final TeamDPlus.code.domain.account.QAccount account;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPostAnswer postAnswer;

    public QPostAnswerComment(String variable) {
        this(PostAnswerComment.class, forVariable(variable), INITS);
    }

    public QPostAnswerComment(Path<? extends PostAnswerComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostAnswerComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostAnswerComment(PathMetadata metadata, PathInits inits) {
        this(PostAnswerComment.class, metadata, inits);
    }

    public QPostAnswerComment(Class<? extends PostAnswerComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new TeamDPlus.code.domain.account.QAccount(forProperty("account"), inits.get("account")) : null;
        this.postAnswer = inits.isInitialized("postAnswer") ? new QPostAnswer(forProperty("postAnswer"), inits.get("postAnswer")) : null;
    }

}

