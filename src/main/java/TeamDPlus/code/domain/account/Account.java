package TeamDPlus.code.domain.account;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.dto.AccountDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // h2는 auto, mysql는 IDENTITY
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String profileImg;

    @Column(nullable = false)
    private int career;

    @Column(columnDefinition = "VARCHAR(50) default ''")
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String workTime;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String workEmail;

    @Column(nullable = false)
    private String tendency;

    @Column(columnDefinition = "BIGINT default 0")
    private Long exp;

    @Column(nullable = false)
    private String refreshToken;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String linkedIn;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String brunch;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String instagram;

    @Column(columnDefinition = "VARCHAR(255) default ''")
    private String webPage;

    @Embedded
    private Specialty specialty;

    @Builder
    public Account(String email, String nickname, String content, String profileImg, int career, String phoneNumber, String workTime,
                   String workEmail, String tendency, Long exp, String refreshToken, String linkedIn, String brunch, String instagram, String webPage) {
        this.email = email;
        this.nickname = nickname;
        this.content = content;
        this.profileImg = profileImg;
        this.career = career;
        this.phoneNumber = phoneNumber;
        this.workTime = workTime;
        this.workEmail = workEmail;
        this.tendency = tendency;
        this.exp = exp;
        this.refreshToken = refreshToken;
        this.linkedIn = linkedIn;
        this.brunch = brunch;
        this.instagram = instagram;
        this.webPage = webPage;
    }

    public void initTendency(String requestTendency) {
        this.tendency = requestTendency;
    }

    public void updateExp(int score) {
        this.exp = (long) score;
    }

    public void updateSpecialty(AccountDto.SpecialtyUpdate dto) {
        this.specialty = dto.getSpecialty();
    }

    public void updateProfile(AccountDto.ProfileUpdate dto) {
        this.nickname = dto.getNickname();
        this.content = dto.getIntro_content();
        this.workEmail = dto.getWork_email();
        this.workTime = dto.getWork_time();
        this.linkedIn = dto.getLinked_in();
        this.brunch = dto.getBrunch();
        this.instagram = dto.getInsta();
        this.webPage = dto.getWeb_page();
        this.career = dto.getCareer();
        this.phoneNumber = dto.getPhone_number();
    }



}













