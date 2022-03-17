package TeamDPlus.code.domain.account.rank;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRank is a Querydsl query type for Rank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRank extends EntityPathBase<Rank> {

    private static final long serialVersionUID = 768987895L;

    public static final QRank rank = new QRank("rank");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> rankScore = createNumber("rankScore", Long.class);

    public QRank(String variable) {
        super(Rank.class, forVariable(variable));
    }

    public QRank(Path<? extends Rank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRank(PathMetadata metadata) {
        super(Rank.class, metadata);
    }

}

