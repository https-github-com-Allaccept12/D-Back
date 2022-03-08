package TeamDPlus.code.domain.account;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Specialty {

    private Boolean figma = false;
    private Boolean framer = false;
    private Boolean afterEffect = false;
    private Boolean illustration = false;
    private Boolean sketch = false;
    private Boolean protopie = false;
    private Boolean jira = false;
    private Boolean hotjar = false;
    private Boolean mixpanel = false;
    private Boolean miro = false;
}
