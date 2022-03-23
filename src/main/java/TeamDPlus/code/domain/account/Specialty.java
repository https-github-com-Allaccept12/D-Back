package TeamDPlus.code.domain.account;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Embeddable
@Getter
public class Specialty {

    private Boolean figma ;
    private Boolean framer ;
    private Boolean afterEffect;
    private Boolean illustration;
    private Boolean sketch;
    private Boolean protopie;
    private Boolean jira;
    private Boolean hotjar;
    private Boolean mixpanel;
    private Boolean miro;

    public Specialty() {
        this.figma = false;
        this.framer = false;
        this.afterEffect = false;
        this.illustration = false;
        this.sketch = false;
        this.protopie = false;
        this.jira = false;
        this.hotjar = false;
        this.mixpanel = false;
        this.miro = false;
    }

}
