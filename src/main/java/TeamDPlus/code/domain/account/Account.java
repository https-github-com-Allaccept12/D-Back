package TeamDPlus.code.domain.account;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.dto.request.ProfileUpdateRequestDto;
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
    @GeneratedValue(strategy = GenerationType.AUTO) // h2는 auto, mysql는 IDENTITY
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Lob
    private String content;

    private String profileImg;

    private int career;

    private String phoneNumber;

    private String workTime;

    private String workEmail;

    private String tendency;

    private Long exp;

    private String refreshToken;

    private String linkedIn;

    private String brunch;

    private String instagram;

    private String webPage;

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

    public void initNickname(String requestNickname) {
        this.nickname = requestNickname;
    }

    public void initTendency(String requestTendency) {
        this.tendency = requestTendency;
    }

    public void updateExp(int score) {
        this.exp = (long) score;
    }

    public void updateProfile(ProfileUpdateRequestDto dto) {
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













