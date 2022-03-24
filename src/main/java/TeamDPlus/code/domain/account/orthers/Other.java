package TeamDPlus.code.domain.account.orthers;


import TeamDPlus.code.domain.account.Specialty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Others")
public class Other {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "other_id")
    private Long id;

    @Embedded
    private Specialty otherSpecialty;

    @Builder
    public Other(Specialty specialty) {
        this.otherSpecialty = specialty;
    }

    public void updateOther(Specialty otherSpecialty) {
        this.otherSpecialty = otherSpecialty;
    }
}
