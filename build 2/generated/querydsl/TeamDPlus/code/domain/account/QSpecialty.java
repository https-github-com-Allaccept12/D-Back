package TeamDPlus.code.domain.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpecialty is a Querydsl query type for Specialty
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSpecialty extends BeanPath<Specialty> {

    private static final long serialVersionUID = 1766166279L;

    public static final QSpecialty specialty = new QSpecialty("specialty");

    public final BooleanPath afterEffect = createBoolean("afterEffect");

    public final BooleanPath figma = createBoolean("figma");

    public final BooleanPath framer = createBoolean("framer");

    public final BooleanPath hotjar = createBoolean("hotjar");

    public final BooleanPath illustration = createBoolean("illustration");

    public final BooleanPath jira = createBoolean("jira");

    public final BooleanPath miro = createBoolean("miro");

    public final BooleanPath mixpanel = createBoolean("mixpanel");

    public final BooleanPath protopie = createBoolean("protopie");

    public final BooleanPath sketch = createBoolean("sketch");

    public QSpecialty(String variable) {
        super(Specialty.class, forVariable(variable));
    }

    public QSpecialty(Path<? extends Specialty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpecialty(PathMetadata metadata) {
        super(Specialty.class, metadata);
    }

}

