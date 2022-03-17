package TeamDPlus.code.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -270925194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final TeamDPlus.code.domain.QBaseEntity _super = new TeamDPlus.code.domain.QBaseEntity(this);

    public final StringPath brunch = createString("brunch");

    public final NumberPath<Integer> career = createNumber("career", Integer.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> created = _super.created;

    public final StringPath email = createString("email");

    public final NumberPath<Long> exp = createNumber("exp", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instagram = createString("instagram");

    public final StringPath interest = createString("interest");

    public final StringPath job = createString("job");

    public final StringPath linkedIn = createString("linkedIn");

    //inherited
    public final DateTimePath<java.sql.Timestamp> modified = _super.modified;

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImg = createString("profileImg");

    public final TeamDPlus.code.domain.account.rank.QRank rank;

    public final StringPath refreshToken = createString("refreshToken");

    public final QSpecialty specialty;

    public final StringPath subContent = createString("subContent");

    public final StringPath tendency = createString("tendency");

    public final StringPath titleContent = createString("titleContent");

    public final StringPath workEmail = createString("workEmail");

    public final StringPath workTime = createString("workTime");

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rank = inits.isInitialized("rank") ? new TeamDPlus.code.domain.account.rank.QRank(forProperty("rank")) : null;
        this.specialty = inits.isInitialized("specialty") ? new QSpecialty(forProperty("specialty")) : null;
    }

}

