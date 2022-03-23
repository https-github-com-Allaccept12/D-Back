package TeamDPlus.code.domain.account;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.orthers.Other;
import TeamDPlus.code.domain.account.rank.Rank;
import TeamDPlus.code.dto.request.AccountRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // h2는 auto, mysql는 IDENTITY
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String titleContent;

    private String subContent;

    private String profileImg;

    @Column(nullable = false)
    private int career;

    private String phoneNumber;

    private String workTime;

    private String workEmail;

    private String tendency;

    @Column(columnDefinition = "BIGINT default 0")
    private Long exp;

    private String refreshToken;

    private String linkedIn;

    private String brunch;

    private String instagram;

    private String interest;

    private String job;

    private String bestArtWorkOne;
    private String bestArtWorkTwo;
    private int artWorkCreateCount;
    private int postCreateCount;

    @Embedded
    private Specialty specialty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id",nullable = false)
    private Rank rank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_id",nullable = false)
    private Other other;

    @Builder
    public Account(final String accountName, final String email, final String nickname, final String subContent, final String titleContent, final String profileImg,
                   final int career, final String phoneNumber, final String workTime,
                   final String workEmail, final String tendency, final Long exp, final String refreshToken,
                   final String linkedIn, final String brunch, final String instagram,final Other other,
                   final String interest, final Rank rank, final String job, final Specialty specialty)  {
        this.accountName = accountName;
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
        this.interest = interest;
        this.rank = rank;
        this.job = job;
        this.specialty = specialty;
        this.other = other;
    }

    public void refreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void initTendency(final String requestTendency) {
        this.tendency = requestTendency;
    }

    public void updateExp(final int score) {
        this.exp += (long) score;
    }

    public void updateInterest(final String interest) {
        this.interest = interest;
    }

    public void upArtworkCountCreate() {
        this.artWorkCreateCount += 1;
    }
    public void upPostCountCreate() {
        this.postCreateCount += 1;
    }
    public void setBestArtWork(String bestArtWorkOne, String bestArtWorkTwo) {
        this.bestArtWorkOne = bestArtWorkOne;
        this.bestArtWorkTwo = bestArtWorkTwo;
    }

    public void setInitProfile(final AccountRequestDto.InitProfileSetting dto) {
        this.nickname = dto.getNickname();
        this.job = dto.getJob();
        this.profileImg = dto.getProfile_img();
        this.titleContent = dto.getIntro_content();
        this.workEmail = dto.getWork_email();
        this.workTime = dto.getWork_time();
        this.linkedIn = dto.getLinked_in();
        this.brunch = dto.getBrunch();
        this.instagram = dto.getInsta();
        this.phoneNumber =dto.getPhone_number();
    }
    public void updateIntro(final AccountRequestDto.UpdateAccountIntro dto) {
        this.titleContent = dto.getTitle_content();
        this.subContent = dto.getSub_content();
    }
    public void updateSpecialty(final AccountRequestDto.UpdateSpecialty dto) {
        this.specialty = dto.getSpecialty();
    }



}













