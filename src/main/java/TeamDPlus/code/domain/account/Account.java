package TeamDPlus.code.domain.account;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.dto.request.AccountRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "varchar(255) default ''")
    private String titleContent;

    @Column(columnDefinition = "varchar(255) default ''")
    private String subContent;

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

    private String tendency;

    @Column(columnDefinition = "BIGINT default 0")
    private Long exp;

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
    public Account(final String email, final String nickname, final String subContent,final String titleContent, final String profileImg,
                   final int career, final String phoneNumber, final String workTime,
                   final String workEmail, final String tendency, final Long exp, final String refreshToken,
                   final String linkedIn, final String brunch, final String instagram, final String webPage)  {
        this.email = email;
        this.nickname = nickname;
        this.titleContent = titleContent;
        this.subContent = subContent;
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

    public void refreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void initTendency(final String requestTendency) {
        this.tendency = requestTendency;
    }

    public void updateExp(final int score) {
        this.exp = (long) score;
    }

    public void updateSpecialty(final AccountRequestDto.SpecialtyUpdate dto) {
        this.specialty = dto.getSpecialty();
    }

    public void updateProfile(final AccountRequestDto.ProfileUpdate dto) {
        this.nickname = dto.getNickname();
        this.titleContent = dto.getTitle_content();
        this.subContent = dto.getSub_content();
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













