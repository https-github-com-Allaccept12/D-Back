package TeamDPlus.code.domain.account;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Specialty {

    private boolean figma = false;
    private boolean framer = false;
    private boolean afterEffect = false;
    private boolean illustration = false;
    private boolean sketch = false;
    private boolean protopie = false;
    private boolean jira = false;
    private boolean hotjar = false;
    private boolean mixpanel = false;
    private boolean miro = false;
}
